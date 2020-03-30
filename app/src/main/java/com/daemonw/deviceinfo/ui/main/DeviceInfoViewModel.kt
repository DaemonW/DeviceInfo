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
        dm.getSimInfoBySubscriptionManager()
        i.mafufacturer = dm.vendor()
        i.brand = dm.brand()
        i.model = dm.model()
        i.osVersion = dm.osVersion()
        i.bootloader = dm.bootloader()
        i.buildId = dm.buildId()
        i.fingerPrint = dm.fingerPrint()
        i.buildTime = dm.buildTime()

        i.androidId = dm.androidId()
        i.imei = dm.imei1()
        i.imei2 = dm.imei2()
        i.meid = dm.meid1()
        i.meid2 = dm.meid2()
        i.imsi = dm.imsi1()
        i.imsi2 = dm.imsi2()
        i.phoneNumber1 = dm.phoneNumber1()
        i.phoneNumber2 = dm.phoneNumber2()
        i.iccId1 = dm.simSerial1()
        i.iccId2 = dm.simSerial2()
        i.wifiMac = dm.wifiMac()
        i.bluetoothMac = dm.bluetoothMac()
        this.info.value = i
        return this
    }
}
