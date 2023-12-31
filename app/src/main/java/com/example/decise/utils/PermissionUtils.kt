package com.example.decise.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.decise.data.prefs.PreferenceManager
import javax.inject.Inject


class PermissionUtils @Inject constructor(private val preferenceManager: PreferenceManager) {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    private fun shouldAskPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }


    public fun shouldAskPermission(context: Context, permission: String): Boolean {
        if (shouldAskPermission()) {
            val permissionResult = ActivityCompat.checkSelfPermission(context, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    fun checkPermission(context: Context, permission: String, listener: OnPermissionAskListener) {

        if (shouldAskPermission(context, permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as AppCompatActivity,
                    permission
                )
            ) {
                listener.onPermissionPreviouslyDenied()
            } else {
                if (preferenceManager.isFirstTimeAskingPermission(permission)) {
                    preferenceManager.firstTimeAskingPermission(permission, false)
                    listener.onNeedPermission()
                } else {
                    listener.onPermissionPreviouslyDeniedWithNeverAskAgain()
                }
            }
        } else {
            listener.onPermissionGranted()
        }
    }


    interface OnPermissionAskListener {
        fun onNeedPermission()
        fun onPermissionPreviouslyDenied()
        fun onPermissionPreviouslyDeniedWithNeverAskAgain()
        fun onPermissionGranted()
    }
}