package com.daemonw.deviceinfo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daemonw.deviceinfo.R

class MainFragment : Fragment() {

    private var mNetworkViewModel: NetworkInfoViewModel? = null
    private var mCellularViewModel: CellularViewModel? = null
    private var mDeviceViewModel: DeviceInfoViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNetworkViewModel = ViewModelProviders.of(this).get(NetworkInfoViewModel::class.java)
        // TODO: Use the ViewModel
        mNetworkViewModel?.load()?.get()?.observe(viewLifecycleOwner, Observer {
            Log.e("daemonw", "mac = " + it.mac)
        })

        mCellularViewModel = ViewModelProviders.of(this).get(CellularViewModel::class.java)
        // TODO: Use the ViewModel
        mCellularViewModel?.load()?.get()?.observe(viewLifecycleOwner, Observer {
            Log.e("daemonw", "net operator = " + it.networkOperator)
        })

        mDeviceViewModel = ViewModelProviders.of(this).get(DeviceInfoViewModel::class.java)
        // TODO: Use the ViewModel
        mDeviceViewModel?.load()?.get()?.observe(viewLifecycleOwner, Observer {
            Log.e("daemonw", "androidId = " + it.androidId)
            Log.e("daemonw", "imei = " + it.imei)
            Log.e("daemonw", "phone number = " + it.phoneNumber)
        })
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
