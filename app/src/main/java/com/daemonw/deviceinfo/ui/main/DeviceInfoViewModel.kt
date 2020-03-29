package com.daemonw.deviceinfo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daemonw.deviceinfo.DeviceInfoManager
import com.daemonw.deviceinfo.model.DeviceInfo

class DeviceInfoViewModel : ViewModel() {
    private val info: MutableLiveData<DeviceInfo> = MutableLiveData()

    fun get(): MutableLiveData<DeviceInfo> {
        return info
    }

    fun setDeviceInfo(info: DeviceInfo) {
        this.info.value = info
    }

    fun getDeviceInfo(): DeviceInfo {
        return this.info.value as DeviceInfo
    }

    fun load(): DeviceInfoViewModel {
        val i = DeviceInfo()
        val dm = DeviceInfoManager.get()
        i.androidId = dm.androidId()
        i.imei=dm.imei1()
        i.imei2=dm.imei2()
        i.phoneNumber=dm.phoneNumber()
        this.info.value = i
        return this
    }
}
