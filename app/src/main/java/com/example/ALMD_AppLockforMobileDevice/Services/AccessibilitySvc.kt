package com.example.ALMD_AppLockforMobileDevice.Services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


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
        val sharedPref_AU = getSharedPreferences("afterUnlock", MODE_PRIVATE)
        val editor_AU = sharedPref_AU.edit()
        val afterUnlock: String? = sharedPref_AU.getString("afterUnlock", null)

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)
        val arrayLockedAppsList: ArrayList<String> = ArrayList(lockedAppsList!!)

        val sharedPref_unlockingApp = getSharedPreferences("unlockingApp", MODE_PRIVATE)
        val editor_unlockingApp = sharedPref_unlockingApp.edit()
        val unlockingApp: String? = sharedPref_unlockingApp.getString("unlockingApp", null)

        val newApp: String = accessibilityEvent.packageName.toString()
        if ((newApp != unlockingApp) && (unlockingApp != "com.example.ALMD_AppLockforMobileDevice")) {
            editor_AU.clear()
            editor_AU.apply()
        }

        if (afterUnlock != null) {
            // Do nothing
        } else {
            try {
                val pkgName: String = accessibilityEvent.packageName.toString()
                Log.d("Package Name", pkgName)
                if (accessibilityEvent.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                    if (accessibilityEvent.packageName != null && accessibilityEvent.className != null) {
                        for (i in arrayLockedAppsList.indices) {
                            if (pkgName == arrayLockedAppsList[i]) {
                                editor_unlockingApp.clear()
                                editor_unlockingApp.apply()
                                editor_unlockingApp.putString("unlockingApp", pkgName)
                                editor_unlockingApp.apply()
                                val launchALMDSecurity = packageManager.getLaunchIntentForPackage("com.example.ALMD_AppLockforMobileDevice")
                                startActivity(launchALMDSecurity)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("Package Name", "-------------------------")
            }
        }
    }

    override fun onInterrupt() {}
}