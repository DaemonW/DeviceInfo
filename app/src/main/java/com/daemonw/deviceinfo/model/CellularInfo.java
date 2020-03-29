package com.daemonw.deviceinfo.model;

import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;

import java.util.List;

public class CellularInfo {
    private String networkOperator;
    private String simOperator;
    private String networkCountryIso;
    private String simCountryIso;
    private String simOperatorNumber;
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

    public String getSimOperatorNumber() {
        return simOperatorNumber;
    }

    public void setSimOperatorNumber(String simOperatorNumber) {
        this.simOperatorNumber = simOperatorNumber;
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
}
