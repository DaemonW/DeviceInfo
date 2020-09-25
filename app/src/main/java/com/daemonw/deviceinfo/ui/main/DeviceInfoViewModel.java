package com.daemonw.deviceinfo.ui.main;

import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.model.DeviceInfo;

public class DeviceInfoViewModel extends BaseViewModel<DeviceInfo> {

    public DeviceInfoViewModel load() {
        DeviceInfo i = new DeviceInfo();
        DeviceInfoManager dm = DeviceInfoManager.get();
        i.marketName = dm.marketName();
        i.manufacturer = dm.vendor();
        i.brand = dm.brand();
        i.model = dm.model();
        i.osVersion = dm.osVersion();
        i.incrementalVersion = dm.incrementalVersion();
        i.bootloader = dm.bootloader();
        i.board = dm.board();
        i.display = dm.display();
        i.device = dm.device();
        i.buildId = dm.buildId();
        i.hardware = dm.hardware();
        i.description = dm.description();
        i.fingerPrint = dm.fingerPrint();
        i.buildTime = dm.buildTime();
        i.wifiMac = dm.wifiMac();
        i.bluetoothMac = dm.bluetoothMac();
        setValue(i);
        return this;
    }
}
