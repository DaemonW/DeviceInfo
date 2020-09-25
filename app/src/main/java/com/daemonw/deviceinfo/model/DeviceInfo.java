package com.daemonw.deviceinfo.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfo implements ListInfo{
    public String marketName;
    public String manufacturer;
    public String brand;
    public String model;
    public String device;
    public String display;
    public String osVersion;
    public String incrementalVersion;
    public String bootloader;
    public String board;
    public String buildId;
    public String hardware;
    public String description;
    public String fingerPrint;
    public String buildTime;
    public String wifiMac;
    public String bluetoothMac;

    public DeviceInfo() {
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }


    public List<ItemInfo> toList() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("设备名称", marketName));
        infos.add(new ItemInfo("制造商", manufacturer));
        infos.add(new ItemInfo("品牌", brand));
        infos.add(new ItemInfo("型号", model));
        infos.add(new ItemInfo("机型", device));
        infos.add(new ItemInfo("主板", board));
        infos.add(new ItemInfo("平台", hardware));
        infos.add(new ItemInfo("Bootloader", bootloader));
        infos.add(new ItemInfo("显示版本", display));
        infos.add(new ItemInfo("修订版本", buildId));
        infos.add(new ItemInfo("增量版本", incrementalVersion));
        infos.add(new ItemInfo("系统版本", osVersion));
        infos.add(new ItemInfo("描述", description));
        infos.add(new ItemInfo("指纹", fingerPrint));
        infos.add(new ItemInfo("编译时间", buildTime));
        infos.add(new ItemInfo("WIFI MAC", wifiMac));
        infos.add(new ItemInfo("蓝牙 MAC", bluetoothMac));
        return infos;
    }

}
