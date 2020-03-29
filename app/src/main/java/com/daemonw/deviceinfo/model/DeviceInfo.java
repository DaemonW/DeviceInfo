package com.daemonw.deviceinfo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class DeviceInfo implements Parcelable {

    public String imei;
    public String imei2; // imei2
    public String meid1; // imei2
    public String meid2; // imei2
    public String androidId;
    public String wifiMac;
    public String bluetoothMac;
    public String phoneNumber;

    public String sim1Id;
    public String sim2Id;
    public String sim1Serial;
    public String sim2Serial;

    public String iccId;
    public String serial;
    public String gmsAdId;

    public final Map<String, String> buildProp = new HashMap<>();

    public DeviceInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imei);
        dest.writeString(this.imei2);
        dest.writeString(this.meid1);
        dest.writeString(this.meid2);
        dest.writeString(this.androidId);
        dest.writeString(this.wifiMac);
        dest.writeString(this.bluetoothMac);
        dest.writeString(this.sim1Id);
        dest.writeString(this.sim2Id);
        dest.writeString(this.sim1Serial);
        dest.writeString(this.sim2Serial);
        dest.writeString(this.iccId);
        dest.writeString(this.serial);
        dest.writeString(this.gmsAdId);
        dest.writeInt(this.buildProp.size());
        for (Map.Entry<String, String> entry : this.buildProp.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public DeviceInfo(Parcel in) {
        this.imei = in.readString();
        this.imei2 = in.readString();
        this.meid1 = in.readString();
        this.meid2 = in.readString();
        this.androidId = in.readString();
        this.wifiMac = in.readString();
        this.bluetoothMac = in.readString();
        this.sim1Id = in.readString();
        this.sim2Id = in.readString();
        this.sim1Serial = in.readString();
        this.sim2Serial = in.readString();
        this.iccId = in.readString();
        this.serial = in.readString();
        this.gmsAdId = in.readString();
        int buildPropSize = in.readInt();
        for (int i = 0; i < buildPropSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.buildProp.put(key, value);
        }
    }

    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel source) {
            return new DeviceInfo(source);
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };

    public String getProp(String key) {
        return buildProp.get(key);
    }

    public void setProp(String key, String value) {
        buildProp.put(key, value);
    }
}
