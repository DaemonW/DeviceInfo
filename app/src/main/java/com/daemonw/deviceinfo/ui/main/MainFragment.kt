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
import com.blankj.utilcode.util.ShellUtils
import com.daemonw.deviceinfo.R
import java.io.File
import java.io.FileWriter
import java.io.StringReader
import java.util.*
import kotlin.collections.HashMap

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
            R.id.export_device -> {
                exportDeviceInfo()
                return true
            }

            R.id.export_sys_prop -> {
                exportBuildProp()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exportDeviceInfo() {
        val info = HashMap<String, Any?>()
        info.put("device_info", mDeviceViewModel?.load()?.getDeviceInfo())
        info.put("cell_info", mCellularViewModel?.load()?.getCellularInfo())
        info.put("network_info", mNetworkViewModel?.load()?.getNetworkInfo())
        val content = GsonUtils.toJson(info)
        val dir = context?.getExternalFilesDir("info")
        val success = FileIOUtils.writeFileFromString(dir?.absolutePath + "/device_info", content)
        if (success) {
            Toast.makeText(context, R.string.tip_export_device_success, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.tip_export_device_failed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun exportBuildProp() {
        val result = ShellUtils.execCmd(arrayListOf("adb shell", "getprop"), false)
        if (result.result != 0) {
            return
        }
        val propStr = result.successMsg
        val properties = Properties()
        val fis = StringReader(propStr)
        try {
            properties.load(fis)
            fis.close()
            val itor = properties.entries.iterator()
            val dir = context?.getExternalFilesDir("info")
            val fos = FileWriter(File(dir, "build.prop.csv"))
            fos.write("key,value\n")
            while (itor.hasNext()) {
                val item = itor.next()
                val key = trim(item.key as String)
                val value = trim(item.value as String)
                fos.write("$key,$value\n")
            }
            fos.flush()
            fos.close()
            Toast.makeText(context, R.string.tip_export_prop_success, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, R.string.tip_export_prop_failed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun trim(str: String): String {
        return str.substring(1, str.length - 1)
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
