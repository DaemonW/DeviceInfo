package com.daemonw.deviceinfo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daemonw.deviceinfo.DeviceInfoManager
import com.daemonw.deviceinfo.model.CellularInfo

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
        i.networkOperatorName = dm.networkOperatorName()
        i.simOperator = dm.simOperator()
        i.networkCountryIso = dm.networkCountryIso()
        i.simCountryIso = dm.simCountryIso()
        i.simOperatorName = dm.simOperatorName()
        i.simState = dm.simState()
        i.cellInfos = dm.cellInfos
        i.cellLocation = dm.cellLocation
        i.neighboringCellInfos = dm.neighboringCellInfos
        this.info.value = i
        return this
    }
}
