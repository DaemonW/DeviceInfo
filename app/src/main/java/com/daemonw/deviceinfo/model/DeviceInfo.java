package com.daemonw.deviceinfo.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfo {
    private String mafufacturer;
    private String brand;
    private String model;
    private String osVersion;
    private String bootloader;
    private String buildId;
    private String fingerPrint;
    private String buildTime;


    public String androidId;
    public String imei;
    public String imei2;
    public String imsi;
    public String imsi2;
    public String meid;
    public String meid2;
    public String phoneNumber1;
    public String phoneNumber2;
    public String iccId1;
    public String iccId2;
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

    public String getIccId1() {
        return iccId1;
    }

    public void setIccId1(String iccId1) {
        this.iccId1 = iccId1;
    }

    public String getIccId2() {
        return iccId2;
    }

    public void setIccId2(String iccId2) {
        this.iccId2 = iccId2;
    }

    public String getMafufacturer() {
        return mafufacturer;
    }

    public void setMafufacturer(String mafufacturer) {
        this.mafufacturer = mafufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBootloader() {
        return bootloader;
    }

    public void setBootloader(String bootloader) {
        this.bootloader = bootloader;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }


    public List<ItemInfo> toInfoList() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("制造商", mafufacturer));
        infos.add(new ItemInfo("品牌", brand));
        infos.add(new ItemInfo("型号", model));
        infos.add(new ItemInfo("系统版本", osVersion));
        infos.add(new ItemInfo("Bootloader", bootloader));
        infos.add(new ItemInfo("Build Id", buildId));
        infos.add(new ItemInfo("指纹", fingerPrint));
        infos.add(new ItemInfo("编译时间", buildTime));

        infos.add(new ItemInfo("Android Id", androidId));
        infos.add(new ItemInfo("IMEI 1", imei));
        infos.add(new ItemInfo("IMEI 2", imei2));
        infos.add(new ItemInfo("MEID 1", meid));
        infos.add(new ItemInfo("MEID 2", meid2));
        infos.add(new ItemInfo("IMSI 1", imsi));
        infos.add(new ItemInfo("IMSI 2", imsi2));
        infos.add(new ItemInfo("电话号码 1", phoneNumber1));
        infos.add(new ItemInfo("电话号码 2", phoneNumber2));
        infos.add(new ItemInfo("ICCID 1", iccId1));
        infos.add(new ItemInfo("ICCID 2", iccId2));
        infos.add(new ItemInfo("WIFI MAC", wifiMac));
        infos.add(new ItemInfo("蓝牙 MAC", bluetoothMac));
        return infos;
    }

}
