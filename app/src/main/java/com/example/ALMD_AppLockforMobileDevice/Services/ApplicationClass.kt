package com.example.ALMD_AppLockforMobileDevice.Services

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class ApplicationClass: MultiDexApplication(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    init {
        instance = this
    }

    val mALMDActivityLifecycleCallbacks = ALMDActivityLifecycleCallbacks()

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(mALMDActivityLifecycleCallbacks)
    }

    companion object {
        private var instance: ApplicationClass? = null

        fun currentActivity(): Activity? {

            return instance!!.mALMDActivityLifecycleCallbacks.currentActivity
        }
    }

}