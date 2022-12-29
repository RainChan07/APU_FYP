package com.example.ALMD_AppLockforMobileDevice.Services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.ALMD_AppLockforMobileDevice.Security.EnterPINActivity
import com.example.ALMD_AppLockforMobileDevice.Security.UserVerificationActivity
import java.lang.Exception


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AccessibilitySvc : AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()

        //Configure these here for compatibility with API 13 and below.
        val config = AccessibilityServiceInfo()
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        serviceInfo = config
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        try {
            val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
            val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)
            val arrayLockedAppsList: ArrayList<String> = ArrayList(lockedAppsList!!)
            val sharedPref_toggleBiometrics = getSharedPreferences("allowBiometrics", MODE_PRIVATE)
            val onOrOff: Boolean = sharedPref_toggleBiometrics.getBoolean("onOrOff", true)

            val pkgName: String = accessibilityEvent.packageName.toString()
            Log.d("Package Name", pkgName)
            if (accessibilityEvent.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (accessibilityEvent.packageName != null && accessibilityEvent.className != null) {
                    val componentName = ComponentName(accessibilityEvent.packageName.toString(), accessibilityEvent.className.toString())
                    val activityInfo = tryGetActivity(componentName)
                    val isActivity = activityInfo != null
                    if (isActivity) Log.i("CurrentActivity", componentName.flattenToShortString())

                    for (i in arrayLockedAppsList.indices) {
                        if ((pkgName == arrayLockedAppsList[i]) && onOrOff) {
                            val toUserVerification = Intent(this, UserVerificationActivity::class.java)
                            startActivity(toUserVerification)
                        } else if ((pkgName == arrayLockedAppsList[i]) && !onOrOff) {
                            val toEnterPin = Intent(this, EnterPINActivity::class.java)
                            startActivity(toEnterPin)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Locked Apps List", "equals to null")
        }
    }

    private fun tryGetActivity(componentName: ComponentName): ActivityInfo? {
        return try {
            packageManager.getActivityInfo(componentName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    override fun onInterrupt() {}
}