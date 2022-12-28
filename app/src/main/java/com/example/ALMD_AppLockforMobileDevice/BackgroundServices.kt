package com.example.ALMD_AppLockforMobileDevice

import android.app.ActivityManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.util.*


class BackgroundServices : Service(){

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int{

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)
        val arrayLockedAppsList: ArrayList<String> = ArrayList(lockedAppsList!!)

//        val am = getSystemService(IntentService.ACTIVITY_SERVICE) as ActivityManager
//        val rt = am.getRunningTasks(1)
//        val ar = rt[0]
//        val activityOnTop = ar.topActivity!!.className

//        val mActivityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val mPackageName = mActivityManager.runningAppProcesses[0].processName

        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn: ComponentName? = am.getRunningTasks(1).get(0).topActivity

        for (i in lockedAppsList.indices) {
            Log.d("Application Name:", cn.toString())
            if (cn.toString() == arrayLockedAppsList[i]) {
                val toUserVerification = Intent(this, UserVerificationActivity::class.java)
                startActivity(toUserVerification)
            }
        }

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

