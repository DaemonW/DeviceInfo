package com.daemonw.deviceinfo;

import android.app.Application;

import com.daemonw.deviceinfo.model.OkHttpFactory;
import com.daemonw.deviceinfo.model.db.DatabaseManager;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DatabaseManager.initialize(this, "db-pass", "device.db");
        DeviceInfoManager.init(this);
        OkHttpFactory.init();
    }

    public static synchronized App get() {
        return instance;
    }
}
