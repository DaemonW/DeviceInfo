package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.daemonw.deviceinfo.model.SensorInfo;
import com.daemonw.deviceinfo.model.SensorItem;

public class SensorViewModel extends BaseViewModel<SensorInfo> {

    @Override
    public SensorViewModel load(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        SensorInfo info = new SensorInfo();
        info.pressureSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_PRESSURE));
        info.gravitySensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_GRAVITY));
        info.gyroscopeSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
        info.accelerometerSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        info.lineAccelerometerSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
        info.magneticSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
        info.proximitySensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_PROXIMITY));
        info.lightSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_LIGHT));
        info.temperatureSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
        info.orientationSensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_ORIENTATION));
        info.humiditySensor = SensorItem.from(sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY));
        this.setValue(info);
        return this;
    }
}
