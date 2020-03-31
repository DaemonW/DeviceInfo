package com.daemonw.deviceinfo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.GsonUtils
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_device_info, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.export -> {
                exportDeviceInfo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun exportDeviceInfo() {
        val info = HashMap<String, Any?>()
        info.put("device_info", mDeviceViewModel?.load()?.getDeviceInfo())
        info.put("cell_info", mCellularViewModel?.load()?.getCellularInfo())
        info.put("network_info", mNetworkViewModel?.load()?.getNetworkInfo())
        val content = GsonUtils.toJson(info)
        val dir = context?.getExternalFilesDir("info")
        val success = FileIOUtils.writeFileFromString(dir?.absolutePath + "/device_info", content)
        if(success){
            Toast.makeText(context, "导出成功", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "导出失败", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
