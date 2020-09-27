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
        i.setVendor(getVendorForRouter(context, i.getBSSID()));

        i.setNetworkOperator(dm.networkOperator());
        i.setNetworkOperatorName(dm.networkOperatorName());
        i.setSimOperator(dm.simOperator());
        i.setNetworkCountryIso(dm.networkCountryIso());
        i.setSimCountryIso(dm.simCountryIso());
        i.setSimOperatorName(dm.simOperatorName());
        int state = dm.simState();
        i.setSimState(getSimStateDescription(state));
        i.setCellInfo(dm.getCellInfo());
        i.setCellLocation(dm.getCellLocation());
        i.setNeighboringCellInfo(dm.getNeighboringCellInfo());
        setValue(i);
        return this;
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

