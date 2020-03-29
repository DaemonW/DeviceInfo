package com.daemonw.deviceinfo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daemonw.deviceinfo.DeviceInfoManager
import com.daemonw.deviceinfo.model.CellularInfo
import com.daemonw.deviceinfo.model.NetworkInfo

class CellularViewModel : ViewModel() {
    private val info: MutableLiveData<CellularInfo> = MutableLiveData()

    fun get(): MutableLiveData<CellularInfo> {
        return info
    }

    fun setCellularInfo(info: CellularInfo) {
        this.info.value = info
    }

    fun getCellularInfo(): CellularInfo {
        return this.info.value as CellularInfo
    }

    fun load(): CellularViewModel {
        val i = CellularInfo()
        val dm = DeviceInfoManager.get()
        i.networkOperator = dm.networkOperator()
        i.simOperator = dm.simOperator()
        i.networkCountryIso = dm.networkCountryIso()
        i.simCountryIso = dm.simCountryIso()
        i.simOperatorNumber = dm.simOperatorNumber()
        this.info.value = i
        return this
    }
}
