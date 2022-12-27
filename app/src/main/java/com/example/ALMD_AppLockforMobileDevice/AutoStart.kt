package com.example.ALMD_AppLockforMobileDevice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AutoStart : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val startServiceIntent = Intent(context, BackgroundServices::class.java)
        context.startService(startServiceIntent)
    }
}