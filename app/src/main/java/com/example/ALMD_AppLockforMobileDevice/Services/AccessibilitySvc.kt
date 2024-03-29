// Programmer Name: Mr.Chan Yu Heng
// Program Name: ALMD_AppLockforMobileDevice
// Description: To lock mobile applications
// First Written on: Monday, 10-Nov-2022
// Edited on: Tuesday, 2-Jan-2023

package com.example.ALMD_AppLockforMobileDevice.Services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import kotlin.collections.ArrayList


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AccessibilitySvc : AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()

        val config = AccessibilityServiceInfo()
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        serviceInfo = config
    }

    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent) {
        try {
            val sharedPref_AU = getSharedPreferences("afterUnlock", MODE_PRIVATE)
            val editor_AU = sharedPref_AU.edit()
            val afterUnlock: String? = sharedPref_AU.getString("afterUnlock", null)

            val sharedPref_unlockingApp = getSharedPreferences("unlockingApp", MODE_PRIVATE)
            val editor_unlockingApp = sharedPref_unlockingApp.edit()
            val unlockingApp: String? = sharedPref_unlockingApp.getString("unlockingApp", null)

            val sharedPref_locked = getSharedPreferences("locked", MODE_PRIVATE)
            val editor_locked = sharedPref_locked.edit()

            val currentApp: String = accessibilityEvent.packageName.toString()

            if ((currentApp != unlockingApp) && (unlockingApp != "com.example.ALMD_AppLockforMobileDevice")) {
                editor_AU.clear()
                editor_AU.apply()
            }

            if (afterUnlock != null) {
                // Do nothing
            } else {
                try {
                    val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
                    val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)
                    val arrayLockedAppsList: ArrayList<String> = ArrayList(lockedAppsList!!)

                    val pkgName: String = accessibilityEvent.packageName.toString()

                    if (accessibilityEvent.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                        if (accessibilityEvent.packageName != null && accessibilityEvent.className != null) {
                            for (i in arrayLockedAppsList.indices) {
                                if (pkgName == arrayLockedAppsList[i]) {
                                    editor_unlockingApp.clear()
                                    editor_unlockingApp.apply()
                                    editor_unlockingApp.putString("unlockingApp", pkgName)
                                    editor_unlockingApp.apply()
                                    editor_locked.putString("locked", "true")
                                    editor_locked.apply()
                                    val launchALMDSecurity = packageManager.getLaunchIntentForPackage("com.example.ALMD_AppLockforMobileDevice")
                                    startActivity(launchALMDSecurity)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    override fun onInterrupt() {}
}