package com.daemonw.deviceinfo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daemonw.deviceinfo.model.DeviceInfo

class CellularViewModel : ViewModel() {
    private val info: MutableLiveData<DeviceInfo>? = null


    fun setDeviceInfo(info: DeviceInfo) {
        this.info?.value = info
    }

    fun getDeviceInfo(info: DeviceInfo): DeviceInfo {
        return this.info?.value as DeviceInfo
    }
}
