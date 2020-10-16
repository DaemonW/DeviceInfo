package com.daemonw.deviceinfo.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfo implements ListInfo {
    public String marketName;
    public String manufacturer;
    public String brand;
    public String model;
    public String platform;
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
        infos.add(new ItemInfo("设备名称", marketName, "marketName"));
        infos.add(new ItemInfo("制造商", manufacturer, "manufacturer"));
        infos.add(new ItemInfo("品牌", brand, "brand"));
        infos.add(new ItemInfo("型号", model, "model"));
        infos.add(new ItemInfo("平台", platform, "platform"));
        infos.add(new ItemInfo("机型", device, "device"));
        infos.add(new ItemInfo("主板", board, "board"));
        infos.add(new ItemInfo("硬件", hardware, "hardware"));
        infos.add(new ItemInfo("Bootloader", bootloader, "bootloader"));
        infos.add(new ItemInfo("显示版本", display, "display"));
        infos.add(new ItemInfo("修订版本", buildId, "buildId"));
        infos.add(new ItemInfo("增量版本", incrementalVersion, "incrementalVersion"));
        infos.add(new ItemInfo("系统版本", osVersion, "osVersion"));
        infos.add(new ItemInfo("描述", description, "description"));
        infos.add(new ItemInfo("指纹", fingerPrint, "fingerPrint"));
        infos.add(new ItemInfo("编译时间", buildTime, "buildTime"));
        infos.add(new ItemInfo("WIFI MAC", wifiMac, "wifiMac"));
        infos.add(new ItemInfo("蓝牙 MAC", bluetoothMac, "bluetoothMac"));
        return infos;
    }

}
