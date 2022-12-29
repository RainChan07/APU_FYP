package com.example.ALMD_AppLockforMobileDevice.AppLockApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ALMD_AppLockforMobileDevice.R

class DevNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer_notes)

        val devNotesBackBtn = findViewById<Button>(R.id.devNotes_backBtn)
        devNotesBackBtn.setOnClickListener {
            val toGetStarted = Intent(this, GetStartedActivity::class.java)
            startActivity(toGetStarted)
        }

        val devNotesNextBtn = findViewById<Button>(R.id.devNotes_nextBtn)
        devNotesNextBtn.setOnClickListener {
            val toMasterPINActivity = Intent(this, MasterPINActivity::class.java)
            startActivity(toMasterPINActivity)
        }
    }

    override fun onBackPressed() {
        return
    }
}