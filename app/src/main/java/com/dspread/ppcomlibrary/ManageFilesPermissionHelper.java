package com.dspread.ppcomlibrary;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class ManageFilesPermissionHelper {

    public interface OnManageAllFilesPermissionResult {
        void onGranted();
        void onDenied();
    }
    private static OnManageAllFilesPermissionResult callback;
    private static boolean permissionState = false;
    public static void requestPermission(Activity activity, OnManageAllFilesPermissionResult resultCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                // Permission already granted
                resultCallback.onGranted();
            } else {
                callback = resultCallback;
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                Log.e("requestPermission", "activity is " + activity.getPackageName());
                //intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        } else {
            // No need for this permission on Android 10 and below
            resultCallback.onGranted();
        }
    }

    // Call this method in Activity's onResume to check the permission result
    public static void onResumeCheck() {
        //Log.e("requestPermission", "onResumeCheck," + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) + "," + callback);
        if (permissionState)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && callback != null) {
            if (Environment.isExternalStorageManager()) {
                callback.onGranted();
                permissionState = true;// Prevent repeated triggering
            } else {
                callback.onDenied();
            }
        }
    }
}
