package com.daemonw.deviceinfo.model;

import android.hardware.Sensor;

public class SensorItem {
    private String name;
    private String vendor;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static SensorItem from(Sensor sensor) {
        if (sensor == null) {
            return null;
        }
        SensorItem item = new SensorItem();
        item.name = sensor.getName();
        item.vendor = sensor.getVendor();
        item.type = sensor.getType();
        return item;
    }
}
