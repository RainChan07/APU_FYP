package com.example.ALMD_AppLockforMobileDevice

import android.app.ActivityManager
import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.IBinder
import java.util.*

class BackgroundServices : Service(){

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int{
        val packageManager = packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)

        val pm: PackageManager = packageManager
        val allApps: MutableList<String> = ArrayList()
        val packs: List<ResolveInfo> = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA)

        for (i in packs.indices) {
            val r: ResolveInfo = packs[i]
            r.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
            allApps.add(r.loadLabel(packageManager).toString())
        }

        val sortedApps = allApps.sort()

        val am = getSystemService(IntentService.ACTIVITY_SERVICE) as ActivityManager
        val rt = am.getRunningTasks(1)
        val ar = rt[0]
        var activityOnTop = ar.topActivity!!.className

//        for (i in lockedAppsList!!.indices) {
//            if (activityOnTop == lockedAppsList[i]) {
//
//            }
//        }

//        val lockIntent = Intent(mContext, LockScreen::class.java)
//        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        mContext.startActivity(lockIntent)
//
//        val startHomescreen = Intent(Intent.ACTION_MAIN)
//        startHomescreen.addCategory(Intent.CATEGORY_HOME)
//        startHomescreen.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(startHomescreen)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }
}

