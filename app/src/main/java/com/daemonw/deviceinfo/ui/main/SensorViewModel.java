package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.daemonw.deviceinfo.model.SensorInfo;
import com.daemonw.deviceinfo.model.SensorItem;

import java.util.ArrayList;
import java.util.List;

public class SensorViewModel extends BaseViewModel<SensorInfo> {

    @Override
    public SensorViewModel load(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        SensorInfo info = new SensorInfo();
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);
        int num = sensors == null ? 0 : sensors.size();
        SensorItem[] sensorItems = new SensorItem[num];
        if (sensors != null) {
            for (int i = 0; i < num; i++) {
                sensorItems[i] = SensorItem.from(sensors.get(i));
            }
        }
        info.setSensors(sensorItems);
        this.setValue(info);
        return this;
    }
}
