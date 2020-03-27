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
}
