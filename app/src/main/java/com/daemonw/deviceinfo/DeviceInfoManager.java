package com.daemonw.deviceinfo;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.daemonw.deviceinfo.util.Reflect;

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

    private DeviceInfoManager(Context context) {
        this.context = context;
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        bm = (BluetoothManager) context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        //sm = (SubscriptionManager) SubscriptionManager.from(context);
        sm = (SubscriptionManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
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
        return imei;
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
        return meid;
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

    public String imsi(int slotIndex) {
        //return tm.getSubscriberId();
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == slotIndex) {
                    return Reflect.on(tm).call("getSubscriberId", info.getSubscriptionId()).get();
                }
            }
        }
        return Reflect.on(tm).call("getSubscriberId", slotIndex).get();
    }

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
        return Reflect.on(tm).call("getSubscriberId", 0).get();
    }

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
        return Reflect.on(tm).call("getSubscriberId", 1).get();
    }

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

    public String phoneNumber(int slotIndex) {
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == slotIndex) {
                    String number = Reflect.on(tm).call("getLine1Number", info.getSubscriptionId()).get();
                    Log.e("daemonw", String.format("card%d number is: %s", slotIndex, number));
                    return number;
                }
            }
        }
        return Reflect.on(tm).call("getLine1Number", slotIndex).get();
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
    public String iccID(int slotIndex) {
        List<SubscriptionInfo> list = sm.getActiveSubscriptionInfoList();
        if (list != null) {
            for (SubscriptionInfo info : list) {
                if (info.getSimSlotIndex() == slotIndex) {
                    String number = info.getIccId();
                    return number;
                }
            }
        }
        return "";
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

    public String baseOS() {
        return Build.VERSION.BASE_OS;
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
                        sb.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
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
        return android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }

//    public String bluetoothMac() {
//        try {
//            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
//            // 参数值为true，禁用访问控制检查
//            field.setAccessible(true);
//            Object bluetoothManagerService = field.get(bluetoothAdapter);
//            if (bluetoothManagerService == null) {
//                return null;
//            }
//            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
//            Object address = method.invoke(bluetoothManagerService);
//            if (address != null && address instanceof String) {
//                return (String) address;
//            } else {
//                return null;
//            }
//            //抛一个总异常省的一堆代码...
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

    public String simState() {
        return String.valueOf(tm.getSimState());
    }

    public List<CellInfo> getCellInfos() {
        return tm.getAllCellInfo();
    }

    public CellLocation getCellLocation() {
        return tm.getCellLocation();
    }

    @TargetApi(28)
    public List<NeighboringCellInfo> getNeighboringCellInfos() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return tm.getNeighboringCellInfo();
        }
        return null;
    }
}
