package com.daemonw.deviceinfo.model;

import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorInfo implements ListInfo {
    public static final int TYPE_TILT_DETECTOR = 22;
    public static final int TYPE_WAKE_GESTURE = 23;
    public static final int TYPE_GLANCE_GESTURE = 24;
    public static final int TYPE_PICK_UP_GESTURE = 25;
    public static final int TYPE_WRIST_TILT_GESTURE = 26;
    public static final int TYPE_DEVICE_ORIENTATION = 27;

    public static final int TYPE_DYNAMIC_SENSOR_META = 32;
    public static final int TYPE_ADDITIONAL_INFO = 33;


    SensorItem[] sensors;

    @Override
    public List<ItemInfo> toList() {
        List<ItemInfo> info = new ArrayList<>();
        for (SensorItem item : sensors) {
            String informalName = getInformalSensorName(item.getType(), item.getName());
            info.add(new ItemInfo(informalName, "", ItemInfo.TYPE_ITEM_HEADER));
            info.addAll(getSensorInfo(item));
        }
        return info;
    }

    private String getInformalSensorName(int sensorType, String sensorName) {
        String name = sensorName;
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                name = "加速传感器";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                name = "磁力传感器";
                break;
            case Sensor.TYPE_ORIENTATION:
                name = "方向传感器";
                break;
            case Sensor.TYPE_GYROSCOPE:
                name = "陀螺仪";
                break;
            case Sensor.TYPE_LIGHT:
                name = "光线传感器";
                break;
            case Sensor.TYPE_PRESSURE:
                name = "压力传感器";
                break;
            case Sensor.TYPE_TEMPERATURE:
                name = "温度传感器";
                break;
            case Sensor.TYPE_PROXIMITY:
                name = "接近传感器";
                break;
            case Sensor.TYPE_GRAVITY:
                name = "重力传感器";
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                name = "线性加速传感器";
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                name = "旋转矢量传感器";
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                name = "湿度传感器";
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                name = "外部温度传感器";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                name = "磁力传感器(未校准)";
                break;
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                name = "游戏旋转矢量传感器";
                break;
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                name = "陀螺仪(未校准)";
                break;
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                name = "特殊动作传感器";
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                name = "步行检测传感器";
                break;
            case Sensor.TYPE_STEP_COUNTER:
                name = "计步传感器";
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                name = "地磁旋转矢量传感器";
                break;
            case Sensor.TYPE_HEART_RATE:
                name = "心率传感器";
                break;
            case TYPE_TILT_DETECTOR:
                name = "倾斜传感器";
                break;
            case TYPE_WAKE_GESTURE:
                name = "手势唤醒传感器";
                break;
            case TYPE_GLANCE_GESTURE:
                name = "掠过手势传感器";
                break;
            case TYPE_PICK_UP_GESTURE:
                name = "拾起手势传感器";
                break;
            case TYPE_WRIST_TILT_GESTURE:
                name = "手腕倾斜传感器";
                break;
            case TYPE_DEVICE_ORIENTATION:
                name = "设备方向传感器";
                break;
            case Sensor.TYPE_POSE_6DOF:
                name = "6自由度姿势传感器";
                break;
            case Sensor.TYPE_STATIONARY_DETECT:
                name = "静止检测传感器";
                break;
            case Sensor.TYPE_MOTION_DETECT:
                name = "运动检测传感器";
                break;
            case Sensor.TYPE_HEART_BEAT:
                name = "心跳传感器";
                break;
            case TYPE_DYNAMIC_SENSOR_META:
                name = "动态传感器元数据";
                break;
            case TYPE_ADDITIONAL_INFO:
                name = "附加信息";
                break;
            case Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT:
                name = "低延迟身体检测传感器";
                break;
            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                name = "加速传感器(未校准)";
                break;
            default:
                name = name.toUpperCase();
                break;
        }
        return name;
    }

    public SensorItem[] getSensors() {
        return sensors;
    }

    public void setSensors(SensorItem[] sensors) {
        this.sensors = sensors;
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
        String type = sensor.getType() + "";
        sensorInfo.add(new ItemInfo("类型", type));
        return sensorInfo;
    }
}
