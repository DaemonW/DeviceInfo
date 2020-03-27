package com.daemonw.deviceinfo.ui.main;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daemonw.deviceinfo.DeviceInfoManager
import com.daemonw.deviceinfo.model.NetworkInfo

class NetworkInfoViewModel : ViewModel() {
    private val info: MutableLiveData<NetworkInfo> = MutableLiveData()

    fun get(): MutableLiveData<NetworkInfo> {
        return info
    }

    fun setNetworkInfo(info: NetworkInfo) {
        this.info.value = info
    }

    fun getNetworkInfo(): NetworkInfo {
        return this.info.value as NetworkInfo
    }

    fun load() {
        val i = NetworkInfo()
        val dm = DeviceInfoManager.get()
        i.bssid = dm.wifiBSSID()
        i.ssid = dm.wifiSSID()
        i.networkId = dm.networkId()
        i.mac = dm.macAddress()
        i.vendor = dm.vendor()
        this.info.value = i

    }
}

