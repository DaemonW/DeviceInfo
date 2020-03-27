package com.daemonw.deviceinfo.model;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DeviceInfo implements Parcelable {
    public boolean enable;

    public String deviceId;
    public String deviceId2; // imei2
    public String meid1; // imei2
    public String meid2; // imei2
    public String androidId;
    public String wifiMac;
    public String bluetoothMac;

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
        dest.writeByte(this.enable ? (byte) 1 : (byte) 0);
        dest.writeString(this.deviceId);
        dest.writeString(this.deviceId2);
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
        this.enable = in.readByte() != 0;
        this.deviceId = in.readString();
        this.deviceId2 = in.readString();
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


    public void clear() {
        deviceId = null;
        deviceId2 = null;
        meid1 = null;
        meid2 = null;
        androidId = null;
        wifiMac = null;
        bluetoothMac = null;
        sim2Id = null;
        sim1Id = null;
        sim2Serial = null;
        sim1Serial = null;
        iccId = null;
        serial = null;
        gmsAdId = null;
    }


    public static String generateDeviceId() {
        return generate10(System.currentTimeMillis(), 15);
    }


    public static String generate10(long seed, int length) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateHex(long seed, int length) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int nextInt = random.nextInt(16);
            if (nextInt < 10) {
                sb.append(nextInt);
            } else {
                sb.append((char) ((nextInt - 10) + 'a'));
            }
        }
        return sb.toString();
    }

    private static String generateMac() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int next = 1;
        int cur = 0;
        while (cur < 12) {
            int val = random.nextInt(16);
            if (val < 10) {
                sb.append(val);
            } else {
                sb.append((char) (val + 87));
            }
            if (cur == next && cur != 11) {
                sb.append(":");
                next += 2;
            }
            cur++;
        }
        return sb.toString();
    }

    @SuppressLint("HardwareIds")
    private static String generateSerial() {
        String serial;
        if (Build.SERIAL == null || Build.SERIAL.length() <= 0) {
            serial = "0123456789ABCDEF";
        } else {
            serial = Build.SERIAL;
        }
        List<Character> list = new ArrayList<>();
        for (char c : serial.toCharArray()) {
            list.add(c);
        }
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (Character c : list) {
            sb.append(c.charValue());
        }
        return sb.toString();
    }
}
