package com.daemonw.deviceinfo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {
    private val info: MutableLiveData<T> = MutableLiveData()

    fun get(): MutableLiveData<T> {
        return info
    }

    fun setValue(info: T) {
        this.info.value = info
    }

    fun getValue(): T {
        return this.info.value as T
    }

    abstract fun load(): T
}