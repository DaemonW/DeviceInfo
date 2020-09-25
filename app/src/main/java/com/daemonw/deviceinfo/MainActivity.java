package com.daemonw.deviceinfo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.daemonw.deviceinfo.model.NetworkInfo;
import com.daemonw.deviceinfo.ui.main.CellularViewModel;
import com.daemonw.deviceinfo.ui.main.DeviceInfoViewModel;
import com.daemonw.deviceinfo.ui.main.IdentifierViewModel;
import com.daemonw.deviceinfo.ui.main.ListInfoFragment;
import com.daemonw.deviceinfo.ui.main.NetworkInfoViewModel;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


public class MainActivity extends AppCompatActivity {
    private static final String[] REQUEST_PERM = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.BODY_SENSORS};
    private static final int REQUEST_PERM_CODE = 1101;

    private static final String[] PAGE = new String[]{"Hardware", "Identifier", "Network"};
    private TabLayout mTab;
    private ViewPager mPager;
    private Toolbar mToolbar;

    private NetworkInfoViewModel mNetworkViewModel;
    private DeviceInfoViewModel mDeviceViewModel;
    private IdentifierViewModel mIdentifierViewModel;
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDeviceViewModel = new ViewModelProvider(MainActivity.this).get(DeviceInfoViewModel.class);
        mNetworkViewModel = new ViewModelProvider(MainActivity.this).get(NetworkInfoViewModel.class);
        mIdentifierViewModel = new ViewModelProvider(MainActivity.this).get(IdentifierViewModel.class);
        mTab = findViewById(R.id.tab_layout);
        mPager = findViewById(R.id.view_pager);
        mPager.setAdapter(new InfoViewPager(getSupportFragmentManager()));
        mTab.setupWithViewPager(mPager);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                mToolbar.setTitle(PAGE[pos]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTab.getTabAt(0).select();
        mToolbar.setTitle(PAGE[0]);
        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        mReceiver = new Receiver();
        registerReceiver(mReceiver, filter);
        if (isPermissionGranted()) {
            loadData();
        } else {
            ActivityCompat.requestPermissions(this, REQUEST_PERM, REQUEST_PERM_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu, inflater);
        getMenuInflater().inflate(R.menu.menu_device_info, menu);
        return true;
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

    private boolean isPermissionGranted() {
        for (String perm : REQUEST_PERM) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERM_CODE) {
            if (isPermissionGranted()) {
                loadData();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Tip")
                        .setMessage("you need to reserve som permissions to collect device information!")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, REQUEST_PERM, REQUEST_PERM_CODE);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
                dialog.show();
            }
        }
    }

    private void loadData() {
        mDeviceViewModel.load();
        mNetworkViewModel.load();
        mIdentifierViewModel.load();
    }

    class InfoViewPager extends FragmentPagerAdapter {
        public InfoViewPager(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            String fragmentTag = PAGE[position].toUpperCase();
            switch (fragmentTag) {
                case "NETWORK":
                    fragment = ListInfoFragment.newInstance(mNetworkViewModel);
                    break;
                case "HARDWARE":
                    fragment = ListInfoFragment.newInstance(mDeviceViewModel);
                    break;
                case "IDENTIFIER":
                    fragment = ListInfoFragment.newInstance(mIdentifierViewModel);
                    break;
                default:
                    fragment = ListInfoFragment.newInstance(mDeviceViewModel);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE[position];
        }
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

    private void exportDeviceInfo() {
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("device_info", mDeviceViewModel.load().getValue());
        info.put("identifier_info", mIdentifierViewModel.load().getValue());
        info.put("network_info", mNetworkViewModel.load().getValue());
        String content = GsonUtils.toJson(info);
        Context context = this;
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
        Context context = this;
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
                fos.write(String.format(Locale.getDefault(),"%s,%s\n",key,value));
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
}
