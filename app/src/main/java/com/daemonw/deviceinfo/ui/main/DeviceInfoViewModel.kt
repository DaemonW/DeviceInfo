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
        i.imei = dm.imei1()
        i.imei2 = dm.imei2()
        i.meid = dm.meid1()
        i.meid2 = dm.meid2()
        i.imsi = dm.imsi1()
        i.imsi2 = dm.imsi2()
        i.phoneNumber1 = dm.phoneNumber1()
        i.phoneNumber2 = dm.phoneNumber2()
        i.sim1Serial = dm.simSerial1()
        i.sim2Serial = dm.simSerial2()
        i.wifiMac = dm.wifiMac()
        i.bluetoothMac = dm.bluetoothMac()
        this.info.value = i
        return this
    }
}
