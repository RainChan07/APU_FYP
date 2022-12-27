package com.example.ALMD_AppLockforMobileDevice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import androidx.core.content.ContextCompat
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

        val toMainMenuActivity = Intent(this, MainMenuActivity::class.java)
        val toEnterPIN = Intent(this,EnterPINActivity::class.java)

        fingerprintLogin = findViewById(R.id.userVerification_fingerprint)
        masterPinLogin = findViewById(R.id.userVerification_PIN)

        masterPinLogin.setOnClickListener {
            startActivity(toEnterPIN)
        }

        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Toast.makeText(this, "Biometrics are available", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(this, "Biometrics are not available\nTry using Master PIN instead", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(this, "Biometrics are not available at the moment\nTry using Master PIN instead", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(this, "Try using Master PIN instead", Toast.LENGTH_SHORT).show()
            }
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
                startActivity(toMainMenuActivity)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use Master PIN instead")
            .build()

        fingerprintLogin.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}