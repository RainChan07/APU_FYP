package com.example.ALMD_AppLockforMobileDevice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ALMD_AppLockforMobileDevice.R

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_started)

        val sharedPref_masterPin = getSharedPreferences("masterPin", MODE_PRIVATE)
        val master_Pin: String? = sharedPref_masterPin.getString("masterPIN", null)

        val sharedPref_toggleBiometrics = getSharedPreferences("allowBiometrics", MODE_PRIVATE)
        val onOrOff: Boolean = sharedPref_toggleBiometrics.getBoolean("onOrOff", true)

        if (master_Pin != null && onOrOff) {
            val toUserVerification = Intent(this,UserVerificationActivity::class.java)
            startActivity(toUserVerification)
        } else if (master_Pin != null) {
            val toEnterPin = Intent(this,EnterPINActivity::class.java)
            startActivity(toEnterPin)
        }

        val getStartedNextBtn = findViewById<Button>(R.id.getStarted_nextBtn)
        getStartedNextBtn.setOnClickListener {
            val toDevNotes = Intent(this,DevNotesActivity::class.java)
            startActivity(toDevNotes)
        }
    }
}