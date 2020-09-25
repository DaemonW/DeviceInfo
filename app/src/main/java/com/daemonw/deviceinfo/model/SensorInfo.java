package com.daemonw.deviceinfo.model;

import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorInfo implements ListInfo {
    //气压
    public Sensor pSensor;
    //重力
    public Sensor gSensor;
    //陀螺仪
    public Sensor gyrSensor;
    //磁强计
    public Sensor mSensor;
    //测距
    public Sensor dSensor;
    //加速
    public Sensor aSensor;
    //线性加速
    public Sensor laSensor;
    //光感
    public Sensor lSensor;
    //方向传感器
    public Sensor oSensor;
    //温度传感器
    public Sensor temSensor;
    //湿度传感器
    public Sensor hSensor;

    @Override
    public List<ItemInfo> toList() {
        List<ItemInfo> info = new ArrayList<>();
        if (pSensor == null) {
            info.add(new ItemInfo("压力感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("压力感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(pSensor));
        }
        if (gSensor == null) {
            info.add(new ItemInfo("重力感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("重力感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(gSensor));
        }
        if (oSensor == null) {
            info.add(new ItemInfo("方向感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("方向感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(oSensor));
        }
        if (gyrSensor == null) {
            info.add(new ItemInfo("陀螺仪", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("陀螺仪", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(gyrSensor));
        }
        if (mSensor == null) {
            info.add(new ItemInfo("磁力感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("磁力感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(mSensor));
        }
        if (dSensor == null) {
            info.add(new ItemInfo("测距仪", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("测距仪", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(dSensor));
        }
        if (aSensor == null) {
            info.add(new ItemInfo("加速计", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("加速计", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(aSensor));
        }
        if (laSensor == null) {
            info.add(new ItemInfo("线性加速计", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("线性加速计", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(laSensor));
        }
        if (lSensor == null) {
            info.add(new ItemInfo("光感器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("光感器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(lSensor));
        }

        if (temSensor == null) {
            info.add(new ItemInfo("温度感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("温度感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(temSensor));
        }

        if (hSensor == null) {
            info.add(new ItemInfo("湿度感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("湿度感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(hSensor));
        }
        return info;
    }

    private List<ItemInfo> getSensorInfo(Sensor sensor) {
        if (sensor == null) {
            return null;
        }
        List<ItemInfo> sensorInfo = new ArrayList<>();
        String name = sensor.getName();
        sensorInfo.add(new ItemInfo("名称", name));
        String vendor = sensor.getVendor();
        sensorInfo.add(new ItemInfo("厂商", vendor));
        String type = sensor.getType()+"";
        sensorInfo.add(new ItemInfo("类型", type));
        return sensorInfo;
    }
}
