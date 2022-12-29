package com.example.ALMD_AppLockforMobileDevice.Services

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log


class ForegroundServices : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            while (true) {
                Log.e("Service", "Service is running...")
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

//        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val cn: ComponentName? = am.getRunningTasks(1).get(0).topActivity
//
//        for (i in lockedAppsList.indices) {
//            Log.d("Application Name:", cn.toString())
//            if (cn.toString() == arrayLockedAppsList[i]) {
//                val toUserVerification = Intent(this, UserVerificationActivity::class.java)
//                startActivity(toUserVerification)
//            }
//        }

//        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
//        val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)
//        val arrayLockedAppsList: ArrayList<String> = ArrayList(lockedAppsList!!)

//        val mgr = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val tasks = mgr.appTasks
//        val className: String
//        if (tasks != null && !tasks.isEmpty()) {
//            className = tasks[0].taskInfo.topActivity!!.className
//            Log.e("Opened Applications", "$className is opened")
//        }

        val appLication: String = ApplicationClass.currentActivity().toString()

        Log.e("Opened Application", appLication)


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

//    override fun onTaskRemoved(rootIntent: Intent) {
//        val restartServiceIntent = Intent(applicationContext, this.javaClass)
//        restartServiceIntent.setPackage(packageName)
//        startService(restartServiceIntent)
//        super.onTaskRemoved(rootIntent)
//    }

    override fun onCreate() {
        val CHANNELID = "Foreground Service ID"
        val channel = NotificationChannel(
            CHANNELID,
            CHANNELID,
            NotificationManager.IMPORTANCE_LOW
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification: Notification.Builder = Notification.Builder(this, CHANNELID)
            .setContentText("Service is running")
            .setContentTitle("Service enabled")

        startForeground(1001, notification.build())

        super.onCreate()
    }
}