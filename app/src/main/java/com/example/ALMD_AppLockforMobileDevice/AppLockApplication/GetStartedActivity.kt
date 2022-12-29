package com.example.ALMD_AppLockforMobileDevice.AppLockApplication

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ALMD_AppLockforMobileDevice.R
import com.example.ALMD_AppLockforMobileDevice.Security.EnterPINActivity
import com.example.ALMD_AppLockforMobileDevice.Security.UserVerificationActivity
import com.example.ALMD_AppLockforMobileDevice.Services.ForegroundServices


class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

//        startService(Intent(applicationContext, BackgroundServices::class.java))

        if (!foregroundServiceRunning()) {
            val foregroundServiceIntent = Intent(this, ForegroundServices::class.java)
            startForegroundService(foregroundServiceIntent)

            Toast.makeText(applicationContext, "service is running", Toast.LENGTH_SHORT).show()
        }

        val sharedPref_masterPin = getSharedPreferences("masterPin", MODE_PRIVATE)
        val master_Pin: String? = sharedPref_masterPin.getString("masterPIN", null)

        val sharedPref_toggleBiometrics = getSharedPreferences("allowBiometrics", MODE_PRIVATE)
        val onOrOff: Boolean = sharedPref_toggleBiometrics.getBoolean("onOrOff", true)

        if (master_Pin != null && onOrOff) {
            val toUserVerification = Intent(this, UserVerificationActivity::class.java)
            startActivity(toUserVerification)
        } else if (master_Pin != null) {
            val toEnterPin = Intent(this, EnterPINActivity::class.java)
            startActivity(toEnterPin)
        }

        val getStartedNextBtn = findViewById<Button>(R.id.getStarted_nextBtn)
        getStartedNextBtn.setOnClickListener {
            val toDevNotes = Intent(this, DevNotesActivity::class.java)
            startActivity(toDevNotes)
        }
    }

    fun foregroundServiceRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (ForegroundServices::class.java.getName() == service.service.className) {
                return true
            }
        }
        return false
    }
}