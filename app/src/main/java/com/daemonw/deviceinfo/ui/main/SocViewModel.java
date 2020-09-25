package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.model.SocInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class SocViewModel extends BaseViewModel<SocInfo> {

    @Override
    public SocViewModel load(Context context) {
        SocInfo info = new SocInfo();
        DeviceInfoManager dm = DeviceInfoManager.get();
        HashMap<String, String> infoMap = new HashMap<>();
        info.cpuInfo = catCpuInfoFile(infoMap);
        info.cpu = infoMap.get("Hardware");
        info.features = infoMap.get("Features");
        info.processor = infoMap.get("Processor");
        info.core = infoMap.get("CPU architecture");
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        info.gpu = sp.getString("gpu", "");
        info.gpuVendor = sp.getString("gpu_vendor", "");
        info.gpuVersion = sp.getString("gpu_version", "");
        this.setValue(info);
        return this;
    }

    private String catCpuInfoFile(HashMap<String, String> infoMap) {
        String info = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                String[] kv = line.split(":");
                if (kv.length == 2) {
                    String key = kv[0].trim();
                    String value = kv[1].trim();
                    //Log.e("daemonw", String.format(Locale.getDefault(), "%s = %s", key, value));
                    infoMap.put(key, value);
                }
                sb.append(line).append("\n");
            }
            info = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
}
