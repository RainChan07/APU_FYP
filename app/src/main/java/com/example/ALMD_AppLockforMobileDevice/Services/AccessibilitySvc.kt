package com.example.ALMD_AppLockforMobileDevice.Services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast


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

            val sharedPref_unlockingApp = getSharedPreferences("unlockingApp", MODE_PRIVATE)
            val editor_unlockingApp = sharedPref_unlockingApp.edit()
            val unlockingApp: String? = sharedPref_unlockingApp.getString("unlockingApp", null)

            val pkgName: String = accessibilityEvent.packageName.toString()
            Log.d("Package Name", pkgName)
            if (accessibilityEvent.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (accessibilityEvent.packageName != null && accessibilityEvent.className != null) {
                    if (unlockingApp != null) {
                        editor_unlockingApp.clear()
                    }
                    for (i in arrayLockedAppsList.indices) {
                        if (pkgName == arrayLockedAppsList[i]) {
                            editor_unlockingApp.putString("unlockingApp", pkgName)
                            editor_unlockingApp.apply()
                            val launchALMDSecurity = packageManager.getLaunchIntentForPackage("com.example.ALMD_AppLockforMobileDevice")
                            startActivity(launchALMDSecurity)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Locked Apps List", "equals to null")
        }
    }

    override fun onInterrupt() {}
}