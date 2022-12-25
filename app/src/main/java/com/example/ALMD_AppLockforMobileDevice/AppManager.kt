package com.example.ALMD_AppLockforMobileDevice

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import java.util.*


class AppManager {
    private var mContext: Context? = null
    private var appInfo: AppInfo? = null
    private var myApps: ArrayList<AppInfo>? = null

    fun AppManager(c: Context) {
        mContext = c
        myApps = java.util.ArrayList()
    }

    fun getApps(): ArrayList<AppInfo>? {
        loadApps()
        return myApps
    }

    private fun loadApps() {
        var packages = mContext!!.packageManager.getInstalledApplications(0)
        for (packageInfo in packages) {
            var newApp = AppInfo()
            newApp.setAppName(getApplicationLabelByPackageName(packageInfo.packageName))
            newApp.setAppPackage(packageInfo.packageName)
            newApp.setAppIcon(getAppIconByPackageName(packageInfo.packageName))
            myApps!!.add(newApp)
        }
        Collections.sort(myApps, object : Comparator<AppInfo> {
            override fun compare(s1: AppInfo, s2: AppInfo): Int {
                return s1.getAppName()!!.compareTo(s2.getAppName()!!, ignoreCase = true)
            }
        })
    }

    // Custom method to get application icon by package name
    private fun getAppIconByPackageName(packageName: String): Drawable? {
        var icon: Drawable?
        icon = try {
            mContext!!.packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            // Get a default icon
            ContextCompat.getDrawable(mContext!!, R.drawable.ic_launcher_background)
        }
        return icon
    }

    // Custom method to get application label by package name
    private fun getApplicationLabelByPackageName(packageName: String): String? {
        var packageManager = mContext!!.packageManager
        var applicationInfo: ApplicationInfo
        var label = "Unknown"
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            if (applicationInfo != null) {
                label = packageManager.getApplicationLabel(applicationInfo) as String
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return label
    }
}