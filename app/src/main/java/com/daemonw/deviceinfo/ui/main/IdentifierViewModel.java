package com.daemonw.deviceinfo.ui.main;

import android.content.Context;

import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.model.IdentifierInfo;

public class IdentifierViewModel extends BaseViewModel<IdentifierInfo> {

    @Override
    public IdentifierViewModel load(Context context) {
        IdentifierInfo i = new IdentifierInfo();
        DeviceInfoManager dm = DeviceInfoManager.get();
        i.androidId = dm.androidId();
        i.serial = dm.serial();
        i.imei1 = dm.imei(0);
        i.imei2 = dm.imei(1);
        i.meid1 = dm.meid(0);
        i.meid2 = dm.meid(1);
        i.imsi1 = dm.imsi(0);
        i.imsi2 = dm.imsi(1);
        i.phoneNumber1 = dm.phoneNumber(0);
        i.phoneNumber2 = dm.phoneNumber(1);
        i.iccId1 = dm.iccID(0);
        i.iccId2 = dm.iccID(1);
        setValue(i);
        return this;
    }
}
