package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;

import com.daemonw.deviceinfo.model.SensorInfo;

public class SensorViewModel extends BaseViewModel<SensorInfo> {

    @Override
    public SensorViewModel load(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        SensorInfo info = new SensorInfo();
        info.pSensor = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
        info.gSensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        info.gyrSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        info.aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        info.laSensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        info.mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        info.dSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        info.lSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        info.temSensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        info.oSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        info.hSensor = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        this.setValue(info);
        return this;
    }
}
