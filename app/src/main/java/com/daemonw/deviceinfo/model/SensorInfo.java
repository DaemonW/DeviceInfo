package com.daemonw.deviceinfo.model;

import java.util.ArrayList;
import java.util.List;

public class SensorInfo implements ListInfo {
    //气压
    public SensorItem pressureSensor;
    //重力
    public SensorItem gravitySensor;
    //陀螺仪
    public SensorItem gyroscopeSensor;
    //磁强计
    public SensorItem magneticSensor;
    //测距
    public SensorItem proximitySensor;
    //加速
    public SensorItem accelerometerSensor;
    //线性加速
    public SensorItem lineAccelerometerSensor;
    //光感
    public SensorItem lightSensor;
    //方向传感器
    public SensorItem orientationSensor;
    //温度传感器
    public SensorItem temperatureSensor;
    //湿度传感器
    public SensorItem humiditySensor;

    @Override
    public List<ItemInfo> toList() {
        List<ItemInfo> info = new ArrayList<>();
        if (pressureSensor == null) {
            info.add(new ItemInfo("压力感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("压力感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(pressureSensor));
        }
        if (gravitySensor == null) {
            info.add(new ItemInfo("重力感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("重力感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(gravitySensor));
        }
        if (orientationSensor == null) {
            info.add(new ItemInfo("方向感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("方向感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(orientationSensor));
        }
        if (gyroscopeSensor == null) {
            info.add(new ItemInfo("陀螺仪", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("陀螺仪", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(gyroscopeSensor));
        }
        if (magneticSensor == null) {
            info.add(new ItemInfo("磁力感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("磁力感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(magneticSensor));
        }
        if (proximitySensor == null) {
            info.add(new ItemInfo("距离感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("距离感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(proximitySensor));
        }
        if (accelerometerSensor == null) {
            info.add(new ItemInfo("加速计", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("加速计", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(accelerometerSensor));
        }
        if (lineAccelerometerSensor == null) {
            info.add(new ItemInfo("线性加速计", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("线性加速计", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(lineAccelerometerSensor));
        }
        if (lightSensor == null) {
            info.add(new ItemInfo("光感器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("光感器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(lightSensor));
        }

        if (temperatureSensor == null) {
            info.add(new ItemInfo("温度感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("温度感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(temperatureSensor));
        }

        if (humiditySensor == null) {
            info.add(new ItemInfo("湿度感应器", "未配置", ItemInfo.TYPE_ITEM_HEADER));
        } else {
            info.add(new ItemInfo("湿度感应器", "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(humiditySensor));
        }
        return info;
    }

    private List<ItemInfo> getSensorInfo(SensorItem sensor) {
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
