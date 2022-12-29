package com.example.ALMD_AppLockforMobileDevice.AppLockApplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ALMD_AppLockforMobileDevice.R

class MainMenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val toggleBiometrics: Switch = findViewById(R.id.toggleBiometrics)

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val editor_lockedAppsList = sharedPref_lockedAppsList.edit()

        val sharedPref_toggleBiometrics = getSharedPreferences("allowBiometrics", MODE_PRIVATE)
        val editor_toggleBiometrics = sharedPref_toggleBiometrics.edit()
        val onOrOff: Boolean = sharedPref_toggleBiometrics.getBoolean("onOrOff", false)

        toggleBiometrics.isChecked = onOrOff

        toggleBiometrics.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor_toggleBiometrics.putBoolean("onOrOff", true)
                editor_toggleBiometrics.apply()
                toggleBiometrics.isChecked = true
            } else {
                editor_toggleBiometrics.putBoolean("onOrOff", false)
                editor_toggleBiometrics.apply()
                toggleBiometrics.isChecked = false
            }
        }

        val mainMenuModifyLockedAppsBtn = findViewById<Button>(R.id.mainMenu_modifyLockedAppsBtn)
        mainMenuModifyLockedAppsBtn.setOnClickListener {
            editor_lockedAppsList.clear()
            editor_lockedAppsList.apply()

            val toMobileApplicationsSelection = Intent(this, MobileApplicationsActivity::class.java)
            startActivity(toMobileApplicationsSelection)
        }

        val mainMenuModifyMasterPinBtn = findViewById<Button>(R.id.mainMenu_modifyMasterPinBtn)
        mainMenuModifyMasterPinBtn.setOnClickListener {
            val toMasterPINActivity = Intent(this, MasterPINActivity::class.java)
            startActivity(toMasterPINActivity)
        }
    }

    override fun onBackPressed() {
        return
    }
}