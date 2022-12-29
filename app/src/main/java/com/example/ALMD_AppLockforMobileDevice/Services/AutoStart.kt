package com.example.ALMD_AppLockforMobileDevice.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AutoStart : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, AccessibilitySvc::class.java)
            context.startService(serviceIntent)
        }
    }
}