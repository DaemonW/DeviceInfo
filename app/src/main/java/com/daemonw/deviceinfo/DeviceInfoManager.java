package com.daemonw.deviceinfo;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.daemonw.deviceinfo.util.Reflect;

public class DeviceInfoManager {
    private Context context;
    private TelephonyManager tm;
    private WifiManager wm;
    private static DeviceInfoManager dm;

    private DeviceInfoManager(Context context) {
        this.context = context;
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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

    public String imei() {
        //return manager.getImei();
        return Reflect.on(tm).call("getImei").get();
    }

    public String imei1() {
        //return manager.getImei(0);
        return Reflect.on(tm).call("getImei", 0).get();
    }

    public String imei2() {
        //return manager.getImei(1);
        return Reflect.on(tm).call("getImei", 1).get();
    }

    public String imsi() {
        //return manager.getSubscriberId();
        return Reflect.on(tm).call("getSubscriberId").get();
    }

    public String imsi1() {
        return Reflect.on(tm).call("getSubscriberId", 0).get();
    }

    public String imsi2() {
        return Reflect.on(tm).call("getSubscriberId", 1).get();
    }

    //品牌
    public String brand() {
        return Build.BRAND;
    }

    //电话号码
    public String phoneNumber() {
        //return manager.getLine1Number();
        return Reflect.on(tm).call("getLine1Number").get();
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

    public String macAddress() {
        WifiInfo wi = wm.getConnectionInfo();
        return wi == null ? "" : wi.getMacAddress();
    }
}
