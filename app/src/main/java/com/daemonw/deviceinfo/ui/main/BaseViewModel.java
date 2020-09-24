package com.daemonw.deviceinfo.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<T> extends ViewModel {

    private MutableLiveData<T> info =new  MutableLiveData<T>();

    public MutableLiveData<T> get(){
        return info;
    }

    public void setValue(T info) {
        this.info.setValue(info);;
    }

    public T getValue(){
        return this.info.getValue();
    }

    abstract public BaseViewModel load();
}
