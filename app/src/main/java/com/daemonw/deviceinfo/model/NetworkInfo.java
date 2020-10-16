package com.daemonw.deviceinfo.model;

import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NetworkInfo implements ListInfo{
    String ssid;
    private String bssid;
    private String networkId;
    private String mac;
    private String routerVendor;

    private String networkOperator;
    private String networkOperatorName;
    private String simOperator;
    private String simOperatorName;
    private String networkCountryIso;
    private String simCountryIso;
    private int simState;
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

    public String getRouterVendor() {
        return routerVendor;
    }

    public void setRouterVendor(String vendor) {
        this.routerVendor = vendor;
    }

    public void setSimState(int simState) {
        this.simState = simState;
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
                bssid, ssid, networkId, mac, routerVendor);
    }

    public List<ItemInfo> toList() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("WiFi网络","",ItemInfo.TYPE_ITEM_HEADER));
        infos.add(new ItemInfo("BSSID", bssid,"bssid"));
        infos.add(new ItemInfo("SSID", ssid,"ssid"));
        infos.add(new ItemInfo("网络id", networkId,"networkId"));
        infos.add(new ItemInfo("MAC地址", mac,"mac"));
        infos.add(new ItemInfo("路由器厂商", routerVendor, "routerVendor"));

        infos.add(new ItemInfo("手机网络","",ItemInfo.TYPE_ITEM_HEADER));
        infos.add(new ItemInfo("网络运行商", networkOperator, "networkOperator"));
        infos.add(new ItemInfo("网络运行商名称", networkOperatorName, "networkOperatorName"));
        infos.add(new ItemInfo("运行商国家码", networkCountryIso,"networkCountryIso"));
        infos.add(new ItemInfo("SIM卡运行商", simOperator,"simOperator"));
        infos.add(new ItemInfo("SIM卡运行商名称", simOperatorName,"simOperatorName"));
        infos.add(new ItemInfo("SIM卡国家码", simCountryIso, "simCountryIso"));
        infos.add(new ItemInfo("SIM卡状态", getSimStateDescription(simState), "simState"));
        return infos;
    }

    public static String getSimStateDescription(int state) {
        String stateDescription;
        switch (state) {
            case TelephonyManager
                    .SIM_STATE_ABSENT:
                stateDescription = "absent";
                break;
            case TelephonyManager
                    .SIM_STATE_CARD_RESTRICTED:
                stateDescription = "card_restricted";
                break;
            case TelephonyManager
                    .SIM_STATE_NETWORK_LOCKED:
                stateDescription = "network_locked";
                break;
            case TelephonyManager
                    .SIM_STATE_NOT_READY:
                stateDescription = "not_ready";
                break;
            case TelephonyManager
                    .SIM_STATE_READY:
                stateDescription = "ready";
                break;
            case TelephonyManager
                    .SIM_STATE_PERM_DISABLED:
                stateDescription = "perm_disabled";
                break;
            case TelephonyManager
                    .SIM_STATE_PIN_REQUIRED:
                stateDescription = "pin_required";
                break;
            case TelephonyManager
                    .SIM_STATE_PUK_REQUIRED:
                stateDescription = "puk_required";
                break;
            case TelephonyManager
                    .SIM_STATE_UNKNOWN:
                stateDescription = "unknown";
                break;
            default:
                stateDescription = "unknown";
                break;

        }
        return stateDescription;
    }
}


