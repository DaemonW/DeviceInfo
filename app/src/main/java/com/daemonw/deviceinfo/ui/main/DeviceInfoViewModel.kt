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
        i.manufacturer = dm.vendor()
        i.brand = dm.brand()
        i.model = dm.model()
        i.osVersion = dm.osVersion()
        i.bootloader = dm.bootloader()
        i.board = dm.board()
        i.display =dm.display()
        i.product = dm.product()
        i.device = dm.device()
        i.buildId = dm.buildId()
        i.hardware = dm.hardware()
        i.fingerPrint = dm.fingerPrint()
        i.buildTime = dm.buildTime()
        i.androidId = dm.androidId()
        i.imei1 = dm.imei(0)
        i.imei2 = dm.imei(1)
        i.meid1 = dm.meid(0)
        i.meid2 = dm.meid(1)
        i.imsi1 = dm.imsi(0)
        i.imsi2 = dm.imsi(1)
        i.phoneNumber1 = dm.phoneNumber(0)
        i.phoneNumber2 = dm.phoneNumber(1)
        i.iccId1 = dm.iccID(0)
        i.iccId2 = dm.iccID(1)
        i.wifiMac = dm.wifiMac()
        i.bluetoothMac = dm.bluetoothMac()
        this.info.value = i
        return this
    }
}
