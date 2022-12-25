package com.example.ALMD_AppLockforMobileDevice

import android.graphics.drawable.Drawable

class AppInfo {
    private var appName: String? = null
    private var appPackage: String? = null
    private var appIcon: Drawable? = null
    private var isSelected = false

    fun getAppPackage(): String? {
        return appPackage
    }

    fun setAppPackage(appPackage: String?) {
        this.appPackage = appPackage
    }

    fun getAppIcon(): Drawable? {
        return appIcon
    }

    fun setAppIcon(appIcon: Drawable?) {
        this.appIcon = appIcon
    }

    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    fun getAppName(): String? {
        return appName
    }

    fun setAppName(appName: String?) {
        this.appName = appName
    }
}