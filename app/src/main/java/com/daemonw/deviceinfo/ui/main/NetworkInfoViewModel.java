package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.R;
import com.daemonw.deviceinfo.model.NetworkInfo;

public class NetworkInfoViewModel extends BaseViewModel<NetworkInfo> {
    private static String[] MAC_VENDOR;

    public NetworkInfoViewModel load(Context context) {
        NetworkInfo i = new NetworkInfo();
        DeviceInfoManager dm = DeviceInfoManager.get();
        i.setBSSID(dm.wifiBSSID());
        i.setSSID(dm.wifiSSID());
        i.setNetworkId(dm.networkId());
        i.setMac(dm.wifiMac());
        i.setRouterVendor(getVendorForRouter(context, i.getBSSID()));

        i.setNetworkOperator(dm.networkOperator());
        i.setNetworkOperatorName(dm.networkOperatorName());
        i.setSimOperator(dm.simOperator());
        i.setNetworkCountryIso(dm.networkCountryIso());
        i.setSimCountryIso(dm.simCountryIso());
        i.setSimOperatorName(dm.simOperatorName());
        int state = dm.simState();
        i.setSimState(state);
        i.setCellInfo(dm.getCellInfo());
        i.setCellLocation(dm.getCellLocation());
        i.setNeighboringCellInfo(dm.getNeighboringCellInfo());
        setValue(i);
        return this;
    }

    public static String getVendorForRouter(Context context, String macAddr) {
        if (MAC_VENDOR == null) {
            MAC_VENDOR = context.getResources().getStringArray(R.array.mac_manufacturer);
        }
        if (MAC_VENDOR == null) {
            return "unknown";
        }
        String header = macAddr.substring(0, 8).toUpperCase();
        for (String s : MAC_VENDOR) {
            if (s.startsWith(header)) {
                String[] info = s.split("\\*");
                if (info != null && info.length == 2) {
                    return info[1];
                }
            }
        }
        return "unknown";
    }
}

