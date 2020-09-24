package com.daemonw.deviceinfo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.daemonw.deviceinfo.ui.main.CellularViewModel;
import com.daemonw.deviceinfo.ui.main.DeviceInfoViewModel;
import com.daemonw.deviceinfo.ui.main.ListInfoFragment;
import com.daemonw.deviceinfo.ui.main.NetworkInfoViewModel;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class MainActivity extends AppCompatActivity {
    private static final String[] PAGE = new String[]{"Hardware", "Cellular", "Network"};
    private TabLayout mTab;
    private ViewPager mPager;
    private Toolbar mToolbar;

    private NetworkInfoViewModel mNetworkViewModel;
    private CellularViewModel mCellularViewModel;
    private DeviceInfoViewModel mDeviceViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDeviceViewModel = new ViewModelProvider(MainActivity.this).get(DeviceInfoViewModel.class);
        mCellularViewModel = new ViewModelProvider(MainActivity.this).get(CellularViewModel.class);
        mNetworkViewModel = new ViewModelProvider(MainActivity.this).get(NetworkInfoViewModel.class);
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
        requestPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void requestPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.LOCATION, PermissionConstants.PHONE, PermissionConstants.SENSORS)
                //.rationale { shouldRequest -> {} }
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, MainFragment.newInstance())
//                                .commitNow();
                        mDeviceViewModel.load();
                        mNetworkViewModel.load();
                        mCellularViewModel.load();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        finish();
                    }
                }).request();
    }

    class InfoViewPager extends FragmentPagerAdapter {
        public InfoViewPager(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 1:
                    fragment = ListInfoFragment.newInstance(mCellularViewModel);
                    break;
                case 2:
                    fragment = ListInfoFragment.newInstance(mNetworkViewModel);
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

    private void exportDeviceInfo() {
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("device_info", mDeviceViewModel.load().getValue());
        info.put("cell_info", mCellularViewModel.load().getValue());
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
}
