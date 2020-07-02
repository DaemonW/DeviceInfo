package com.daemonw.deviceinfo.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfo {
    public String manufacturer;
    public String brand;
    public String model;
    public String product;
    public String device;
    public String display;
    public String osVersion;
    public String bootloader;
    public String board;
    public String buildId;
    public String hardware;
    public String fingerPrint;
    public String buildTime;


    public String androidId;
    public String imei1;
    public String imei2;
    public String imsi1;
    public String imsi2;
    public String meid1;
    public String meid2;
    public String phoneNumber1;
    public String phoneNumber2;
    public String iccId1;
    public String iccId2;
    public String wifiMac;
    public String bluetoothMac;

    public DeviceInfo() {
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }


    public List<ItemInfo> toInfoList() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("制造商", manufacturer));
        infos.add(new ItemInfo("品牌", brand));
        infos.add(new ItemInfo("型号", model));
        infos.add(new ItemInfo("产品", product));
        infos.add(new ItemInfo("机型", device));
        infos.add(new ItemInfo("主板", board));
        infos.add(new ItemInfo("Bootloader", bootloader));
        infos.add(new ItemInfo("显示版本", display));
        infos.add(new ItemInfo("修订版本", buildId));
        infos.add(new ItemInfo("系统版本", osVersion));
        infos.add(new ItemInfo("硬件", hardware));
        infos.add(new ItemInfo("指纹", fingerPrint));
        infos.add(new ItemInfo("编译时间", buildTime));

        infos.add(new ItemInfo("Android Id", androidId));
        infos.add(new ItemInfo("IMEI 1", imei1));
        infos.add(new ItemInfo("IMEI 2", imei2));
        infos.add(new ItemInfo("MEID 1", meid1));
        infos.add(new ItemInfo("MEID 2", meid2));
        infos.add(new ItemInfo("IMSI 1", imsi1));
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
