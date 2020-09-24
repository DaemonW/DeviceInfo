package com.daemonw.deviceinfo.model;

import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CellularInfo implements ListInfo{
    private String networkOperator;
    private String networkOperatorName;
    private String simOperator;
    private String simOperatorName;
    private String networkCountryIso;
    private String simCountryIso;
    private String simState;
    private List<CellInfo> cellInfos;
    private CellLocation cellLocation;
    private List<NeighboringCellInfo> neighboringCellInfos;

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }

    public String getSimOperator() {
        return simOperator;
    }

    public void setSimOperator(String simOperator) {
        this.simOperator = simOperator;
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

    public List<CellInfo> getCellInfos() {
        return cellInfos;
    }

    public void setCellInfos(List<CellInfo> cellInfos) {
        this.cellInfos = cellInfos;
    }

    public CellLocation getCellLocation() {
        return cellLocation;
    }

    public void setCellLocation(CellLocation cellLocation) {
        this.cellLocation = cellLocation;
    }

    public List<NeighboringCellInfo> getNeighboringCellInfos() {
        return neighboringCellInfos;
    }

    public void setNeighboringCellInfos(List<NeighboringCellInfo> neighboringCellInfos) {
        this.neighboringCellInfos = neighboringCellInfos;
    }

    public String getNetworkOperatorName() {
        return networkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        this.networkOperatorName = networkOperatorName;
    }

    public String getSimOperatorName() {
        return simOperatorName;
    }

    public void setSimOperatorName(String simOperatorName) {
        this.simOperatorName = simOperatorName;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(
                Locale.getDefault(),
                "networkOperator = %s,\nsimOperator = %s,\nnetworkCountryIso = %s,\nsimCountryIso = %s,\nsimOperatorNumber = %s",
                networkOperator, simOperator, networkCountryIso, simCountryIso, simState);
    }

    public List<ItemInfo> toList() {
        List<ItemInfo> infos = new ArrayList<>();
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
