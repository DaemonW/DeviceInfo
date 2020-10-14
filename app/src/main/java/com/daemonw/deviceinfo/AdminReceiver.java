package com.daemonw.deviceinfo;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import androidx.annotation.NonNull;

public class AdminReceiver extends DeviceAdminReceiver {
    private final String LOG_TAG = AdminReceiver.class.getSimpleName();

    @Override
    public void onPasswordSucceeded(Context context, Intent intent, UserHandle user) {
        Log.d(LOG_TAG, "onPasswordSucceeded");
        super.onPasswordSucceeded(context, intent, user);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(LOG_TAG, "owner onReceive: " + intent);
        super.onReceive(context, intent);
    }

    @Override
    public void onUserRemoved(Context context, Intent intent, UserHandle removedUser) {
        super.onUserRemoved(context, intent, removedUser);
        Log.e(LOG_TAG, removedUser.toString() + " removed");
    }

    @Override
    public void onUserAdded(Context context, Intent intent, @NonNull UserHandle newUser) {
        super.onUserAdded(context, intent, newUser);
        Log.e(LOG_TAG, newUser.toString() + " added");
    }

    /**
     * Called on the new profile when device owner provisioning has completed. Device owner
     * provisioning is the process of setting up the device so that its main profile is managed by
     * the mobile device management (MDM) application set up as the device owner.
     */
    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        // Enable the profile
        Log.d(LOG_TAG, "onProfileProvisioningComplete");
        DevicePolicyManager manager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = getComponentName(context);
        manager.setProfileName(componentName, "administrator");
        // Open the main screen
        Intent launch = new Intent(context, MainActivity.class);
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launch);
    }

    /**
     * @return A newly instantiated {@link ComponentName} for this
     * DeviceAdminReceiver.
     */
    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), AdminReceiver.class);
    }
}