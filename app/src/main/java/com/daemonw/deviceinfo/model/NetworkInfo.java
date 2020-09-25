package com.daemonw.deviceinfo.model;

import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;

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

    private String networkOperator;
    private String networkOperatorName;
    private String simOperator;
    private String simOperatorName;
    private String networkCountryIso;
    private String simCountryIso;
    private String simState;
    private List<CellInfo> cellInfo;
    private CellLocation cellLocation;
    private List<NeighboringCellInfo> neighboringCellInfo;

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

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }

    public String getNetworkOperatorName() {
        return networkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        this.networkOperatorName = networkOperatorName;
    }

    public String getSimOperator() {
        return simOperator;
    }

    public void setSimOperator(String simOperator) {
        this.simOperator = simOperator;
    }

    public String getSimOperatorName() {
        return simOperatorName;
    }

    public void setSimOperatorName(String simOperatorName) {
        this.simOperatorName = simOperatorName;
    }

    public String getNetworkCountryIso() {
        return networkCountryIso;
    }

    public void setNetworkCountryIso(String networkCountryIso) {
        this.networkCountryIso = networkCountryIso;
    }

    public String getSimCountryIso() {
        return simCountryIso;
    }

    public void setSimCountryIso(String simCountryIso) {
        this.simCountryIso = simCountryIso;
    }

    public String getSimState() {
        return simState;
    }

    public void setSimState(String simState) {
        this.simState = simState;
    }

    public List<CellInfo> getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(List<CellInfo> cellInfo) {
        this.cellInfo = cellInfo;
    }

    public CellLocation getCellLocation() {
        return cellLocation;
    }

    public void setCellLocation(CellLocation cellLocation) {
        this.cellLocation = cellLocation;
    }

    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        return neighboringCellInfo;
    }

    public void setNeighboringCellInfo(List<NeighboringCellInfo> neighboringCellInfo) {
        this.neighboringCellInfo = neighboringCellInfo;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "BSSID = %s,\nSSID = %s,\nnetworkId = %s,\nmac = %s,\nvendor = %s",
                bssid, ssid, networkId, mac, manufacturer);
    }

    public List<ItemInfo> toList() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("WiFi网络","",ItemInfo.TYPE_ITEM_HEADER));
        infos.add(new ItemInfo("BSSID", bssid));
        infos.add(new ItemInfo("SSID", ssid));
        infos.add(new ItemInfo("网络id", networkId));
        infos.add(new ItemInfo("MAC地址", mac));
        infos.add(new ItemInfo("制造商", manufacturer));

        infos.add(new ItemInfo("手机网络","",ItemInfo.TYPE_ITEM_HEADER));
        infos.add(new ItemInfo("网络运行商", networkOperator));
        infos.add(new ItemInfo("网络运行商名称", networkOperatorName));
        infos.add(new ItemInfo("运行商国家码", networkCountryIso));
        infos.add(new ItemInfo("SIM卡运行商", simOperator));
        infos.add(new ItemInfo("SIM卡运行商名称", simOperatorName));
        infos.add(new ItemInfo("SIM卡国家码", simCountryIso));
        infos.add(new ItemInfo("SIM卡状态", simState));
        return infos;
    }
}


