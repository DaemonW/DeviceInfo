package com.daemonw.deviceinfo.ui.main;

import android.content.Context;

import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.model.CellularInfo;

public class CellularViewModel extends BaseViewModel<CellularInfo> {
    public CellularViewModel load(Context context){
        CellularInfo i = new CellularInfo();
        DeviceInfoManager dm = DeviceInfoManager.get();
        i.setNetworkOperator(dm.networkOperator());
        i.setNetworkOperatorName(dm.networkOperatorName());
        i.setSimOperator(dm.simOperator());
        i.setNetworkCountryIso(dm.networkCountryIso());
        i.setSimCountryIso(dm.simCountryIso());
        i.setSimOperatorName(dm.simOperatorName());
        i.setSimState(dm.simState());
        i.setCellInfo(dm.getCellInfo());
        i.setCellLocation(dm.getCellLocation());
        i.setNeighboringCellInfo(dm.getNeighboringCellInfo());
        this.setValue(i);
        return this;
    }
}