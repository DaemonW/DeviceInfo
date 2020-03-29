package com.daemonw.deviceinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.daemonw.deviceinfo.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
        requestPermission()
    }

    fun requestPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.LOCATION)
                //.rationale { shouldRequest -> {} }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                    }
                })
                .request()
    }
}
