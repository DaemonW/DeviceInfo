package com.daemonw.deviceinfo.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class TelephonyHelper {
    private Context context;
    private static TelephonyManager manager;

    private TelephonyHelper(Context context) {
        this.context = context;
        manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    private String androidId() {
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }

    private String imei() {
        return manager.getImei();
    }

    private String imei1() {
        return manager.getImei(0);
    }

    private String imei2() {
        return manager.getImei(1);
    }

    public String imsi() {
        return manager.getSubscriberId();
    }

    public String imsi1() {
        return Reflect.on(manager).call("getSubscriberId", 0).get();
    }

    public String imsi2() {
        return Reflect.on(manager).call("getSubscriberId", 1).get();
    }

    //品牌
    private String brand() {
        return Build.BRAND;
    }

    //电话号码
    private String phoneNumber() {
        return manager.getLine1Number();
    }

    //厂商
    private String vendor() {
        return Build.MANUFACTURER;
    }

    //型号
    private String model() {
        return Build.MODEL;
    }

    private String baseOS() {
        return Build.VERSION.BASE_OS;
    }

    //bootloader
    private String bootloader() {
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

}
