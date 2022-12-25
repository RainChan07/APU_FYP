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

        val sharedPref = getSharedPreferences("masterPin", MODE_PRIVATE)
        val masterPIN: String? = sharedPref.getString("masterPin", null)

        if (masterPIN != null) {
            val toMainMenu = Intent(this,MainMenuActivity::class.java)
            startActivity(toMainMenu)
        }

        val getStartedNextBtn = findViewById<Button>(R.id.getStarted_nextBtn)
        getStartedNextBtn.setOnClickListener {
            val toDevNotes = Intent(this,DevNotesActivity::class.java)
            startActivity(toDevNotes)
        }
    }
}