// Programmer Name: Mr.Chan Yu Heng
// Program Name: ALMD_AppLockforMobileDevice
// Description: To lock mobile applications
// First Written on: Monday, 10-Nov-2022
// Edited on: Tuesday, 2-Jan-2023

package com.example.ALMD_AppLockforMobileDevice.Security

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import androidx.core.content.ContextCompat
import com.example.ALMD_AppLockforMobileDevice.AppLockApplication.MainMenuActivity
import com.example.ALMD_AppLockforMobileDevice.R
import java.util.concurrent.Executor

class UserVerificationActivity : AppCompatActivity() {

    private lateinit var fingerprintLogin: Button
    private lateinit var masterPinLogin: Button

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_verification)

        val sharedPref_unlockingApp = getSharedPreferences("unlockingApp", MODE_PRIVATE)
        val unlockingApp: String? = sharedPref_unlockingApp.getString("unlockingApp", null)

        val sharedPref_AU = getSharedPreferences("afterUnlock", MODE_PRIVATE)
        val editor_AU = sharedPref_AU.edit()

        val sharedPref_locked = getSharedPreferences("locked", MODE_PRIVATE)
        val editor_locked = sharedPref_locked.edit()
        val locked: String? = sharedPref_locked.getString("locked", null)

        val toMainMenuActivity = Intent(this, MainMenuActivity::class.java)
        val toEnterPIN = Intent(this, EnterPINActivity::class.java)

        fingerprintLogin = findViewById(R.id.userVerification_fingerprint)
        masterPinLogin = findViewById(R.id.userVerification_PIN)

        masterPinLogin.setOnClickListener {
            startActivity(toEnterPIN)
        }

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d(TAG, "$errorCode :: $errString")
                if(errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    startActivity(toEnterPIN)
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(applicationContext, "Authentication succeeded", Toast.LENGTH_SHORT).show()
                if (locked != null) {
                    editor_AU.putString("afterUnlock", "1")
                    editor_AU.apply()
                    editor_locked.clear()
                    editor_locked.apply()
                    val launchLockedApp = packageManager.getLaunchIntentForPackage(unlockingApp.toString())
                    startActivity(launchLockedApp)
                } else {
                    startActivity(toMainMenuActivity)
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login: ALMD")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use Master PIN instead")
            .build()

        fingerprintLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}