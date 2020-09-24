package com.daemonw.deviceinfo.ui.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.daemonw.deviceinfo.DeviceInfoManager;
import com.daemonw.deviceinfo.R;
import com.daemonw.deviceinfo.model.NetworkInfo;
import com.daemonw.deviceinfo.util.LocationUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MainFragment extends Fragment {

    private NetworkInfoViewModel mNetworkViewModel;
    private CellularViewModel mCellularViewModel;
    private DeviceInfoViewModel mDeviceViewModel;
    private RecyclerView mNetworkInfoList;
    private RecyclerView mCellularInfoList;
    private RecyclerView mDeviceInfoList;
    private InfoAdapter mCellularAdater;
    private InfoAdapter mDeviceAdater;
    private InfoAdapter mNetworkAdater;
    private BroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("daemonw", "onCreateView");
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        Context context = getContext();
        mCellularInfoList = root.findViewById(R.id.cell_info);
        mCellularAdater = new InfoAdapter(context, null);
        mCellularInfoList.setLayoutManager(new LinearLayoutManager(context));
        mCellularInfoList.setAdapter(mCellularAdater);

        mDeviceInfoList = root.findViewById(R.id.device_info);
        mDeviceAdater = new InfoAdapter(context, null);
        mDeviceInfoList.setLayoutManager(new LinearLayoutManager(context));
        mDeviceInfoList.setAdapter(mDeviceAdater);

        mNetworkInfoList = root.findViewById(R.id.network_info);
        mNetworkAdater = new InfoAdapter(context, null);
        mNetworkInfoList.setLayoutManager(new LinearLayoutManager(context));
        mNetworkInfoList.setAdapter(mNetworkAdater);

        mNetworkViewModel = new ViewModelProvider(this).get(NetworkInfoViewModel.class);
        // TODO: Use the ViewModel
        mNetworkViewModel.load().get().observe(getViewLifecycleOwner(), (it) -> {
            Log.e("daemonw", "network info:\n $it");
            mNetworkAdater.setData(it.toList());
            mNetworkAdater.notifyDataSetChanged();
        });

        mCellularViewModel = new ViewModelProvider(this).get(CellularViewModel.class);
        // TODO: Use the ViewModel
        mCellularViewModel.load().get().observe(getViewLifecycleOwner(), (it) -> {
            Log.e("daemonw", "cellular info:\n $it");
            mCellularAdater.setData(it.toList());
            mCellularAdater.notifyDataSetChanged();
        });

        mDeviceViewModel = new ViewModelProvider(this).get(DeviceInfoViewModel.class);
        // TODO: Use the ViewModel
        mDeviceViewModel.load().get().observe(getViewLifecycleOwner(), (it) -> {
            mDeviceAdater.setData(it.toList());
            mDeviceAdater.notifyDataSetChanged();
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        mReceiver = new Receiver();
        getContext().registerReceiver(mReceiver, filter);
        Activity a = getActivity();
        if (!LocationUtil.isLocationServiceOpened(a)) {
            LocationUtil.requestLocationService(a);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        getContext().unregisterReceiver(mReceiver);
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("daemonw", "onActivityCreated");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_device_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.export_device: {
                exportDeviceInfo();
                return true;
            }

            case R.id.export_sys_prop: {
                exportBuildProp();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportDeviceInfo() {
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("device_info", mDeviceViewModel.load().getValue());
        info.put("cell_info", mCellularViewModel.load().getValue());
        info.put("network_info", mNetworkViewModel.load().getValue());
        String content = GsonUtils.toJson(info);
        Context context = getContext();
        File dir = context.getExternalFilesDir("info");
        String fname = Build.BRAND + "_" + Build.MODEL;
        boolean success = FileIOUtils.writeFileFromString(dir.getAbsolutePath() + "/" + fname, content);
        if (success) {
            Toast.makeText(context, R.string.tip_export_device_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.tip_export_device_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void exportBuildProp() {
        ShellUtils.CommandResult result = ShellUtils.execCmd(new String[]{"adb shell", "getprop"}, false);
        if (result.result != 0) {
            return;
        }
        String propStr = result.successMsg;
        Properties properties = new Properties();
        StringReader fis = new StringReader(propStr);
        Context context = getContext();
        try {
            properties.load(fis);
            fis.close();
            Iterator itor = properties.entrySet().iterator();
            File dir = context.getExternalFilesDir("info");
            FileWriter fos = new FileWriter(new File(dir, "build.prop.csv"));
            fos.write("key,value\n");
            while (itor.hasNext()) {
                Map.Entry<String, Object> item = (Map.Entry<String, Object>) itor.next();
                String key = trim(item.getKey());
                String value = trim(item.getValue().toString());
                fos.write("$key,$value\n");
            }
            fos.flush();
            fos.close();
            Toast.makeText(context, R.string.tip_export_prop_success, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, R.string.tip_export_prop_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private String trim(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                NetworkInfo ni = mNetworkViewModel.getValue();
                ni.setSSID(DeviceInfoManager.get().wifiSSID());
                mNetworkViewModel.setValue(ni);
            }
        }
    }

}
