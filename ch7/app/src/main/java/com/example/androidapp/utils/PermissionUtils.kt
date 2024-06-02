package com.example.androidapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    const val REQUEST_CODE_CAMERA = 100
    const val REQUEST_CODE_GALERY = 101

    fun isPermissionGranted(
        context: Context, permissionRequestName:
        String
    ): Boolean {
        return (ContextCompat.checkSelfPermission(context, permissionRequestName) ==
                PackageManager.PERMISSION_GRANTED)
    }

    fun requestPermission(
        activity: Activity, permissionRequestName:
        Array<String>, requestCode: Int
    ) {
        ActivityCompat.requestPermissions(activity, permissionRequestName, requestCode)
    }


}