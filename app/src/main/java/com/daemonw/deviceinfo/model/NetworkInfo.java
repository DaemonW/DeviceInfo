package com.daemonw.deviceinfo.model;

public class NetworkInfo {
     String ssid;
    private String bssid;
    private String networkId;
    private String mac;
    private String vendor;

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
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}


