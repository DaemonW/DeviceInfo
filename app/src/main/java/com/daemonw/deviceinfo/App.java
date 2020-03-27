package com.daemonw.deviceinfo;

import android.app.Application;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DeviceInfoManager.init(this);
    }

    private static App get() {
        return instance;
    }
}
