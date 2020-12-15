package com.daemonw.deviceinfo;

import android.app.Application;
import android.content.SharedPreferences;

import com.daemonw.deviceinfo.model.OkHttpFactory;
import com.daemonw.deviceinfo.model.db.DatabaseManager;
import com.daemonw.deviceinfo.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class App extends Application {
    private static App instance;
    private SharedPreferences setting;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        setting = getSharedPreferences("default", MODE_PRIVATE);
        boolean initializedDb = setting.getBoolean("db_init", false);
        if (!initializedDb) {
            try {
                File db = getDatabasePath("device.db");
                FileOutputStream fos = new FileOutputStream(db);
                InputStream in = getResources().openRawResource(R.raw.device);
                IOUtil.copy(in, fos, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setting.edit().putBoolean("db_init",true).apply();
        }
        DatabaseManager.initialize(this, "db-pass", "device.db");
        DeviceInfoManager.init(this);
        OkHttpFactory.init();
    }

    public static synchronized App get() {
        return instance;
    }

    public SharedPreferences getSetting() {
        return setting;
    }
}
