
if (condition1 == true) {
    // If condition 1 has been met, move over to thisActivity
    
    val toThisActivity = Intent(this, thisActivity::class.java)
    startActivity(toThisActivity)
    
} else if (condition2 == true) {
    // If condition 2 has been met, move over to thatActivity instead
    
    val toThatActivity = Intent(this, thatActivity::class.java)
    startActivity(toThatActivity)
    
} else {
    // If neither conditions have been met, continue with this activity
    
}

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
