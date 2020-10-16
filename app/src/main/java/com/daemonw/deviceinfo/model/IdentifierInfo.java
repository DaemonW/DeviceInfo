package com.daemonw.deviceinfo.model;

import java.util.ArrayList;
import java.util.List;

public class IdentifierInfo implements ListInfo {
    public String androidId;
    public String serial;
    public String imei1;
    public String imei2;
    public String imsi1;
    public String imsi2;
    public String meid1;
    public String meid2;
    public String phoneNumber1;
    public String phoneNumber2;
    public String iccId1;
    public String iccId2;

    @Override
    public List<ItemInfo> toList() {
        List<ItemInfo> info = new ArrayList<>();
        info.add(new ItemInfo("Android Id", androidId,"androidId"));
        info.add(new ItemInfo("SERIAL", serial,"serial"));
        info.add(new ItemInfo("IMEI 1", imei1, "imei1"));
        info.add(new ItemInfo("IMEI 2", imei2,"imei2"));
        info.add(new ItemInfo("MEID 1", meid1,"meid1"));
        info.add(new ItemInfo("MEID 2", meid2,"meid2"));
        info.add(new ItemInfo("IMSI 1", imsi1,"imsi1"));
        info.add(new ItemInfo("IMSI 2", imsi2,"imsi2"));
        info.add(new ItemInfo("电话号码 1", phoneNumber1, "phoneNumber1"));
        info.add(new ItemInfo("电话号码 2", phoneNumber2, "phoneNumber2"));
        info.add(new ItemInfo("ICCID 1", iccId1,"iccId1"));
        info.add(new ItemInfo("ICCID 2", iccId2, "iccId2"));
        return info;
    }
}
