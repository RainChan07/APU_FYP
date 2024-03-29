// Programmer Name: Mr.Chan Yu Heng
// Program Name: ALMD_AppLockforMobileDevice
// Description: To lock mobile applications
// First Written on: Monday, 10-Nov-2022
// Edited on: Tuesday, 2-Jan-2023

package com.example.ALMD_AppLockforMobileDevice.AppLockApplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.example.ALMD_AppLockforMobileDevice.R
import com.example.ALMD_AppLockforMobileDevice.Security.EnterPINActivity
import com.example.ALMD_AppLockforMobileDevice.Security.UserVerificationActivity
import com.example.ALMD_AppLockforMobileDevice.Services.AccessibilitySvc


class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

        runAccessibilityService()

        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPref_masterPin = EncryptedSharedPreferences.create(
            "masterPINFile",
            masterKey,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val master_Pin: String? = sharedPref_masterPin.getString("masterPIN", null)

        val sharedPref_toggleBiometrics = getSharedPreferences("allowBiometrics", MODE_PRIVATE)
        val switchBiometrics: Boolean = sharedPref_toggleBiometrics.getBoolean("switchBiometrics", false)

        if (master_Pin != null && switchBiometrics) {
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

    fun runAccessibilityService() {
        val runAccessibilitySvc = Intent(this, AccessibilitySvc::class.java)
        startService(runAccessibilitySvc)
    }
}