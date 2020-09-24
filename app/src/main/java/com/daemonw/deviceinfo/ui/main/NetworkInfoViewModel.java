package com.daemonw.deviceinfo.ui.main;

import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.model.NetworkInfo;

public class NetworkInfoViewModel extends BaseViewModel<NetworkInfo> {
    public NetworkInfoViewModel load(){
        NetworkInfo i = new NetworkInfo();
        DeviceInfoManager dm = DeviceInfoManager.get();
        i.setBSSID(dm.wifiBSSID());
        i.setSSID(dm.wifiSSID());
        i.setNetworkId(dm.networkId());
        i.setMac(dm.wifiMac());
        i.setVendor("unknown");
        setValue(i);
        return this;
    }
}

