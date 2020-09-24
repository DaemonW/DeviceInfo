package com.daemonw.deviceinfo.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NetworkInfo implements ListInfo{
    String ssid;
    private String bssid;
    private String networkId;
    private String mac;
    private String manufacturer;

    public String getSSID() {
        return ssid;
    }

    public void setSSID(String ssid) {
        this.ssid = ssid;
    }

    public String getBSSID() {
        return bssid;
    }

    public void setBSSID(String bssid) {
        this.bssid = bssid;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getVendor() {
        return manufacturer;
    }

    public void setVendor(String vendor) {
        this.manufacturer = vendor;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "BSSID = %s,\nSSID = %s,\nnetworkId = %s,\nmac = %s,\nvendor = %s",
                bssid, ssid, networkId, mac, manufacturer);
    }

    public List<ItemInfo> toList() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("BSSID", bssid));
        infos.add(new ItemInfo("SSID", ssid));
        infos.add(new ItemInfo("网络id", networkId));
        infos.add(new ItemInfo("MAC地址", mac));
        infos.add(new ItemInfo("制造商", manufacturer));
        return infos;
    }
}


