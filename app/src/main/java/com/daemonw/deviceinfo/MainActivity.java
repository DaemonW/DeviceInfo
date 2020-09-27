package com.daemonw.deviceinfo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.daemonw.deviceinfo.model.DeviceInfo;
import com.daemonw.deviceinfo.model.NetworkInfo;
import com.daemonw.deviceinfo.model.SocInfo;
import com.daemonw.deviceinfo.ui.main.BaseViewModel;
import com.daemonw.deviceinfo.ui.main.SensorViewModel;
import com.daemonw.deviceinfo.ui.main.SocViewModel;
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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {
    private static final String[] REQUEST_PERM = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};
    private static final int REQUEST_PERM_CODE = 1101;
    public static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

    private static final int HARDWARE = 0;
    private static final int SOC = 1;
    private static final int IDENTIFIER = 2;
    private static final int NETWORK = 3;
    private static final int SENSOR = 4;

    public static final int[] PAGE = new int[]{
            R.string.page_hardware, R.string.page_soc, R.string.page_identifier, R.string.page_network, R.string.page_sensor
    };
    private LinearLayout mRootView;
    private TabLayout mTab;
    private ViewPager mPager;
    private Toolbar mToolbar;
    private GLSurfaceView mSurfaceView;

    private NetworkInfoViewModel mNetworkViewModel;
    private DeviceInfoViewModel mDeviceViewModel;
    private IdentifierViewModel mIdentifierViewModel;
    private SocViewModel mSocViewModel;
    private SensorViewModel mSensorModel;
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mRootView = findViewById(R.id.container);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDeviceViewModel = new ViewModelProvider(MainActivity.this).get(DeviceInfoViewModel.class);
        mNetworkViewModel = new ViewModelProvider(MainActivity.this).get(NetworkInfoViewModel.class);
        mIdentifierViewModel = new ViewModelProvider(MainActivity.this).get(IdentifierViewModel.class);
        mSensorModel = new ViewModelProvider(MainActivity.this).get(SensorViewModel.class);
        mSocViewModel = new ViewModelProvider(MainActivity.this).get(SocViewModel.class);
        mTab = findViewById(R.id.tab_layout);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mPager = findViewById(R.id.view_pager);
        mSurfaceView = new GLSurfaceView(this);
        mSurfaceView.setEGLContextClientVersion(1);
        mSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        GLSurfaceView.Renderer r = new SimpleRender();
        mSurfaceView.setRenderer(r);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mSurfaceView.setLayoutParams(lp);
        mRootView.addView(mSurfaceView);
        mPager.setAdapter(new InfoViewPager(getSupportFragmentManager()));
        mTab.setupWithViewPager(mPager);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                int pos = tab.getPosition();
//                mToolbar.setTitle(PAGE[pos]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTab.getTabAt(0).select();
        mToolbar.setTitle(getString(PAGE[0]));
        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(ACTION_SIM_STATE_CHANGED);
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
                        .setTitle(R.string.tip)
                        .setMessage(R.string.tip_need_perm)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gotoAppManagePage();
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

    private void gotoAppManagePage() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void loadData() {
        mDeviceViewModel.load(this);
        mNetworkViewModel.load(this);
        mIdentifierViewModel.load(this);
        mSocViewModel.load(this);
        mSensorModel.load(this);
    }

    class InfoViewPager extends FragmentPagerAdapter {
        public InfoViewPager(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            ListInfoFragment fragment = null;
            Bundle bundle = new Bundle();
            switch (position) {
                case NETWORK:
                    fragment = ListInfoFragment.newInstance(bundle);
                    fragment.setViewModel(mNetworkViewModel);
                    break;
                case HARDWARE:
                    fragment = ListInfoFragment.newInstance(bundle);
                    fragment.setViewModel(mDeviceViewModel);
                    break;
                case IDENTIFIER:
                    fragment = ListInfoFragment.newInstance(bundle);
                    fragment.setViewModel(mIdentifierViewModel);
                    break;
                case SOC:
                    fragment = ListInfoFragment.newInstance(bundle);
                    fragment.setViewModel(mSocViewModel);
                    break;
                case SENSOR:
                    fragment = ListInfoFragment.newInstance(bundle);
                    fragment.setViewModel(mSensorModel);
                    break;
                default:
                    fragment = ListInfoFragment.newInstance(bundle);
                    fragment.setViewModel(mDeviceViewModel);
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
            return getString(PAGE[position]);
        }
    }

    public <T> ViewModel getViewModel(T c) {
        if(c instanceof DeviceInfoViewModel){
            return mDeviceViewModel;
        }else if(c instanceof IdentifierViewModel){
            return mIdentifierViewModel;
        }else if(c instanceof SocViewModel){
            return mSocViewModel;
        }else if(c instanceof SensorViewModel){
            return mSensorModel;
        }else if(c instanceof NetworkInfoViewModel){
            return mNetworkViewModel;
        }else{
            throw new RuntimeException();
        }
    }

    class SimpleRender implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            String _gpu = gl.glGetString(GL10.GL_RENDERER);
            String _gpuVendor = gl.glGetString(GL10.GL_VENDOR);
            String _gpuVersion = gl.glGetString(GL10.GL_VERSION);
            String _gpuExtension = gl.glGetString(GL10.GL_EXTENSIONS);
            runOnUiThread(() -> {
                Log.e("daemonw", "load GPU info");
                SocInfo info = mSocViewModel.load(MainActivity.this).getValue();
                SharedPreferences sp = getApplicationContext().getSharedPreferences("default", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String gpu = sp.getString("gpu", "");
                if (gpu == null || gpu.isEmpty()) {
                    gpu = _gpu;
                    Log.e("daemonw", "gpu = " + gpu);
                    editor.putString("gpu", gpu).apply();
                }
                String vendor = sp.getString("gpu_vendor", "");
                if (vendor == null || vendor.isEmpty()) {
                    vendor = _gpuVendor;
                    Log.e("daemonw", "gpu vendor = " + vendor);
                    editor.putString("gpu_vendor", vendor).apply();
                }
                String version = sp.getString("gpu_version", "");
                if (version == null || version.isEmpty()) {
                    version = _gpuVersion;
                    Log.e("daemonw", "gpu version = " + version);
                    editor.putString("gpu_version", version).apply();
                }
                String extension = sp.getString("gpu_extension", "");
                if (extension == null || extension.isEmpty()) {
                    extension = _gpuExtension;
                    Log.e("daemonw", "gpu extension = " + extension);
                    editor.putString("gpu_extension", extension).apply();
                }
                info.gpu = gpu;
                info.gpuVendor = vendor;
                info.gpuVersion = version;
                mSocViewModel.setValue(info);
            });
        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int i, int i1) {

        }

        @Override
        public void onDrawFrame(GL10 gl10) {

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

            if (action.equals(ACTION_SIM_STATE_CHANGED)) {
                NetworkInfo ni = mNetworkViewModel.getValue();
                String state = NetworkInfoViewModel.getSimStateDescription(DeviceInfoManager.get().simState());
                ni.setSimState(state);
                mNetworkViewModel.setValue(ni);
            }
        }
    }

    private void exportDeviceInfo() {
        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("hardware", mDeviceViewModel.load(this).getValue());
        info.put("identifier", mIdentifierViewModel.load(this).getValue());
        info.put("network", mNetworkViewModel.load(this).getValue());
        info.put("soc", mSocViewModel.load(this).getValue());
        info.put("sensor", mSensorModel.load(this).getValue());
        String content = GsonUtils.toJson(info);
        Context context = this;
        File dir = context.getExternalFilesDir("info");
        String fname = Build.BRAND + "_" + Build.MODEL + "_device_info.json";
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
                fos.write(String.format(Locale.getDefault(), "%s,%s\n", key, value));
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
