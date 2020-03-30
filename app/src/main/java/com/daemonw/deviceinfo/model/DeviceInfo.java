package com.daemonw.deviceinfo.model;

import androidx.annotation.NonNull;

public class DeviceInfo {

    public String androidId;
    public String imei;
    public String imei2;
    public String imsi;
    public String imsi2;
    public String meid;
    public String meid2;
    public String phoneNumber1;
    public String phoneNumber2;
    public String sim1Serial;
    public String sim2Serial;
    public String wifiMac;
    public String bluetoothMac;

    public DeviceInfo() {
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImsi2() {
        return imsi2;
    }

    public void setImsi2(String imsi2) {
        this.imsi2 = imsi2;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid1) {
        this.meid = meid1;
    }

    public String getMeid2() {
        return meid2;
    }

    public void setMeid2(String meid2) {
        this.meid2 = meid2;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getSim1Serial() {
        return sim1Serial;
    }

    public void setSim1Serial(String sim1Serial) {
        this.sim1Serial = sim1Serial;
    }

    public String getSim2Serial() {
        return sim2Serial;
    }

    public void setSim2Serial(String sim2Serial) {
        this.sim2Serial = sim2Serial;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
