package com.example.ALMD_AppLockforMobileDevice

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val editor_lockedAppsList = sharedPref_lockedAppsList.edit()

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

        //switch for fingerprint


    }

    override fun onBackPressed() {
        return
    }
}