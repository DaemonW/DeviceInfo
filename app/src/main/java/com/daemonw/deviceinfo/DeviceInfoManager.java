package com.daemonw.deviceinfo;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.daemonw.deviceinfo.util.FileUtil;
import com.daemonw.deviceinfo.util.IOUtil;
import com.daemonw.deviceinfo.util.Reflect;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class DeviceInfoManager {
    private Context context;
    private TelephonyManager tm;
    private WifiManager wm;
    private BluetoothManager bm;
    private SubscriptionManager sm;
    private static DeviceInfoManager dm;
    private SharedPreferences sp;

    private DeviceInfoManager(Context context) {
        this.context = context;
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        bm = (BluetoothManager) context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        //sm = (SubscriptionManager) SubscriptionManager.from(context);
        sm = (SubscriptionManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        sp = context.getSharedPreferences("system_prop", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        dm = new DeviceInfoManager(context);
    }

    public static DeviceInfoManager get() {
        return dm;
    }

    public String androidId() {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @SuppressLint("MissingPermission")
    public String serial() {
        String serial = sp.getString("ro.serialno", "");
        if (serial != null && !serial.isEmpty()) {
            return serial;
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return Build.SERIAL;
        }
        serial = Build.getSerial();
        if (serial.toUpperCase().equals("UNKNOWN")) {
            serial = getProp("ro.serialno");
        }
        if (serial == null || serial.isEmpty()) {
            serial = getProp("ro.boot.serialno");
        }
        File f = new File(Environment.getExternalStorageDirectory(), "serial.txt");
        if (f.exists()) {
            String content = FileUtil.readString(f.getAbsolutePath(), "utf-8");
            if (content != null && !content.isEmpty()) {
                serial = content.replace("\n","");
                sp.edit().putString("ro.serialno", serial).apply();
                f.delete();
            }
        }
        return serial;
    }

    private String getProp(String propKey) {
        String value = Reflect.on("android.os.SystemProperties").call("get", propKey).get();
        if (value == null || value.isEmpty()) {
            value = getPropByShell(propKey);
            if (value == null) {
                value = "";
            }
        }
        return value;
    }

    private String getPropByShell(String propKey) {
        ShellUtils.CommandResult result = ShellUtils.execCmd("getprop " + propKey, false);
        if (result.result == 0) {
            return result.successMsg;
        }
        return null;
    }

    private final String[] MARKET_NAME_KEY = new String[]{
            "ro.semc.product.name",
            "ro.config.marketing_name",//for huawei
            "ro.product.nickname",
            "ro.config.devicename",
            "persist.sys.devicename",
            "persist.sys.exif.model",
            "ro.product.product.device",//for oneplus
            "ro.oppo.market.name"};//for oppo

    public String marketName() {
        String name = null;
        for (String propKey : MARKET_NAME_KEY) {
            name = getProp(propKey);
            if (!name.isEmpty()) {
                break;
            }
        }
        if (name.isEmpty()) {
            BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
            name = myDevice.getName();
        }
        return name;
    }

    @SuppressLint("MissingPermission")
    public String imei(int slotIndex) {
        String imei = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = tm.getImei(slotIndex);
            } else {
                imei = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei == null ? "" : imei;
    }

    public String imei1() {
        //return manager.getImei(0);
        try {
            return Reflect.on(tm).call("getImei", 0).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String imei2() {
        //return manager.getImei(1);
        try {
            return Reflect.on(tm).call("getImei", 1).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("MissingPermission")
    public String meid(int slotIndex) {
        String meid = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                meid = tm.getMeid(slotIndex);
            } else {
                meid = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meid == null ? "" : meid;
    }

    public String meid1() {
//        return tm.getMeid();
        try {
            return Reflect.on(tm).call("getMeid", 0).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String meid2() {
        //return manager.getMeid(0);
        try {
            return Reflect.on(tm).call("getMeid", 1).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("MissingPermission")
    public String imsi(int slotIndex) {
        //return tm.getSubscriberId();
        String imsi = "";
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null && list.size() != 0) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == slotIndex) {
                    int subId = info.getSubscriptionId();
                    try {
                        imsi = Reflect.on(tm).call("getSubscriberId", subId).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return imsi;
    }

    @SuppressLint("MissingPermission")
    public String imsi1() {
        //return tm.getSubscriberId();
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == 0) {
                    return Reflect.on(tm).call("getSubscriberId", info.getSubscriptionId()).get();
                }
            }
        }
        return Reflect.on(tm).call("getSubscriberId", 1).get();
    }

    @SuppressLint("MissingPermission")
    public String imsi2() {
        //return tm.getSubscriberId();
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == 1) {
                    return Reflect.on(tm).call("getSubscriberId", info.getSubscriptionId()).get();
                }
            }
        }
        return Reflect.on(tm).call("getSubscriberId", 2).get();
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getSimInfoBySubscriptionManager() {
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list == null) {
            return;
        }
        for (SubscriptionInfo info : list) {
            Log.d("Q_M", "ICCID-->" + info.getIccId());
            Log.d("Q_M", "subId-->" + info.getSubscriptionId());
            Log.d("Q_M", "SlotIndex-->" + info.getSimSlotIndex());
            Log.d("Q_M", "Number-->" + info.getNumber());
            Log.d("Q_M", "DisplayName-->" + info.getDisplayName());
            Log.d("Q_M", "CarrierName-->" + info.getCarrierName());
            Log.d("Q_M", "---------------------------------");
        }
    }

    //品牌
    public String brand() {
        return Build.BRAND;
    }


    @SuppressLint("MissingPermission")
    public String phoneNumber(int slotIndex) {
        String number = "";
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == slotIndex) {
                    try {
                        number = Reflect.on(tm).call("getLine1Number", info.getSubscriptionId()).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        if (number == null || number.isEmpty()) {
            number = Reflect.on(tm).call("getLine1Number", slotIndex).get();
        }
        return number == null ? "" : number;
    }

    //电话号码
    public String phoneNumber1() {
//        return tm.getLine1Number();
        try {
            return Reflect.on(tm).call("getLine1Number", 0).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //电话号码
    public String phoneNumber2() {
//        return tm.getLine1Number();
        try {
            return Reflect.on(tm).call("getLine1Number", 1).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //ICCID
    @SuppressLint("MissingPermission")
    public String iccID(int slotIndex) {
        String iccId = "";
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == slotIndex) {
                    iccId = info.getIccId();
                    break;
                }
            }
        }
        if (iccId == null || iccId.isEmpty()) {
            try {
                iccId = Reflect.on(tm).call("getSimSerialNumber", 0).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iccId == null ? "" : iccId;
    }


    //sim串号
    public String simSerial1() {
//        return tm.getSimSerialNumber();
        try {
            return Reflect.on(tm).call("getSimSerialNumber", 0).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //sim串号
    public String simSerial2() {
//        return tm.getSimSerialNumber();
        try {
            return Reflect.on(tm).call("getSimSerialNumber", 1).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //厂商
    public String vendor() {
        return Build.MANUFACTURER;
    }

    //型号
    public String model() {
        return Build.MODEL;
    }

    //显示
    public String display() {
        return Build.DISPLAY;
    }

    public String baseOS() {
        return Build.VERSION.BASE_OS;
    }

    public String incrementalVersion() {
        return getProp("ro.build.version.incremental");
    }

    public String buildTime() {
        return String.valueOf(Build.TIME);
    }

    //bootloader
    public String bootloader() {
        return Build.BOOTLOADER;
    }

    public String buildId() {
        return Build.ID;
    }

    //description
    public String description() {
        return getProp("ro.build.description");
    }

    //指纹
    public String fingerPrint() {
        return Build.FINGERPRINT;
    }

    //os版本
    public String osVersion() {
        return Build.VERSION.RELEASE;
    }

    //主板
    public String board() {
        return Build.BOARD;
    }

    //产品
    public String product() {
        return Build.PRODUCT;
    }

    //机型
    public String device() {
        return Build.DEVICE;
    }

    //硬件
    public String hardware() {
        return Build.HARDWARE;
    }

    //bssid
    public String wifiBSSID() {
        WifiInfo wi = wm.getConnectionInfo();
        return wi == null ? "" : wi.getBSSID();
    }

    //ssid
    public String wifiSSID() {
        WifiInfo wi = wm.getConnectionInfo();
        return wi == null ? "" : wi.getSSID();
    }

    public String networkId() {
        WifiInfo wi = wm.getConnectionInfo();
        return wi == null ? "" : String.valueOf(wi.getNetworkId());
    }

    public String wifiMac() {
        if (Build.VERSION.SDK_INT >= 23) {
            return getWifiMacAddress();
        }
        WifiInfo wi = wm.getConnectionInfo();
        return wi == null ? "" : wi.getMacAddress();
    }

    public static String getWifiMacAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            if (nis == null) {
                return "";
            }
            for (NetworkInterface t : Collections.list(nis)) {
                if (t.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = t.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02X:", hardwareAddress[i]));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    public String bluetoothMac() {
        String mac = getBtMac();
        if (mac == null || mac.isEmpty()) {
            mac = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
        }
        return mac;
    }

    public String getBtMac() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object address = method.invoke(bluetoothManagerService);
            if (address != null && address instanceof String) {
                return (String) address;
            } else {
                return null;
            }
            //抛一个总异常省的一堆代码...
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String networkOperator() {
        return tm.getNetworkOperator();
    }

    public String networkOperatorName() {
        return tm.getNetworkOperatorName();
    }

    public String simOperator() {
        return tm.getSimOperator();
    }

    public String simOperatorName() {
        return tm.getSimOperatorName();
    }

    public String networkCountryIso() {
        return tm.getNetworkCountryIso();
    }

    public String simCountryIso() {
        return tm.getSimCountryIso();
    }

    public int simState() {
        return tm.getSimState();
    }

    @SuppressLint("MissingPermission")
    public List<CellInfo> getCellInfo() {
        return tm.getAllCellInfo();
    }

    @SuppressLint("MissingPermission")
    public CellLocation getCellLocation() {
        return tm.getCellLocation();
    }

    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return null;
        }
        return Reflect.on(tm).call("getNeighboringCellInfo").get();
    }

    public String socPlatform() {
        return getProp("ro.board.platform");
    }
}
