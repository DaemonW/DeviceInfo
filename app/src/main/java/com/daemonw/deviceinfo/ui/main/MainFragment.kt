package com.daemonw.deviceinfo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daemonw.deviceinfo.R

class MainFragment : Fragment() {

    private var mNetworkViewModel: NetworkInfoViewModel? = null
    private var mCellularViewModel: CellularViewModel? = null
    private var mDeviceViewModel: DeviceInfoViewModel? = null
    private var mNetworkInfoList: RecyclerView? = null
    private var mCellularInfoList: RecyclerView? = null
    private var mDeviceInfoList: RecyclerView? = null
    private var mCellularAdater: InfoAdapter? = null
    private var mDeviceAdater: InfoAdapter? = null
    private var mNetworkAdater: InfoAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("daemonw", "onCreateView")
        var root = inflater.inflate(R.layout.main_fragment, container, false)

        mCellularInfoList = root.findViewById(R.id.cell_info)
        mCellularAdater = InfoAdapter(context, null)
        mCellularInfoList?.layoutManager = LinearLayoutManager(context)
        mCellularInfoList?.adapter = mCellularAdater

        mDeviceInfoList = root.findViewById(R.id.device_info)
        mDeviceAdater = InfoAdapter(context, null)
        mDeviceInfoList?.layoutManager = LinearLayoutManager(context)
        mDeviceInfoList?.adapter = mDeviceAdater

        mNetworkInfoList = root.findViewById(R.id.network_info)
        mNetworkAdater = InfoAdapter(context, null)
        mNetworkInfoList?.layoutManager = LinearLayoutManager(context)
        mNetworkInfoList?.adapter = mNetworkAdater

        mNetworkViewModel = ViewModelProviders.of(this).get(NetworkInfoViewModel::class.java)
        // TODO: Use the ViewModel
        mNetworkViewModel?.load()?.get()?.observe(viewLifecycleOwner, Observer {
            Log.e("daemonw", "network info:\n $it")
            mNetworkAdater?.setData(it.toInfoList())
            mNetworkAdater?.notifyDataSetChanged()
        })

        mCellularViewModel = ViewModelProviders.of(this).get(CellularViewModel::class.java)
        // TODO: Use the ViewModel
        mCellularViewModel?.load()?.get()?.observe(viewLifecycleOwner, Observer {
            Log.e("daemonw", "cellular info:\n $it")
            mCellularAdater?.setData(it.toInfoList())
            mCellularAdater?.notifyDataSetChanged()
        })

        mDeviceViewModel = ViewModelProviders.of(this).get(DeviceInfoViewModel::class.java)
        // TODO: Use the ViewModel
        mDeviceViewModel?.load()?.get()?.observe(viewLifecycleOwner, Observer {
            mDeviceAdater?.setData(it.toInfoList())
            mDeviceAdater?.notifyDataSetChanged()
        })
        return root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("daemonw", "onActivityCreated")
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
