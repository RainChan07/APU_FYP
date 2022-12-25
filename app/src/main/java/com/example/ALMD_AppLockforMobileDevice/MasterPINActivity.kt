package com.example.ALMD_AppLockforMobileDevice

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MasterPINActivity : AppCompatActivity() {

    private lateinit var inpCode1: EditText
    private lateinit var inpCode2: EditText
    private lateinit var inpCode3: EditText
    private lateinit var inpCode4: EditText
    private lateinit var inpCode5: EditText
    private lateinit var inpCode6: EditText

    private lateinit var masterPin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.master_pin_initial_setup)

        val sharedPref_masterPin = getSharedPreferences("masterPin", MODE_PRIVATE)
        val master_Pin: String? = sharedPref_masterPin.getString("masterPIN", null)
        val editor_masterPin = sharedPref_masterPin.edit()

        val sharedPref_lockedAppsList = getSharedPreferences("lockedAppsList", MODE_PRIVATE)
        val lockedAppsList: MutableSet<String>? = sharedPref_lockedAppsList.getStringSet("lockedAppsList", null)

        inpCode1 = findViewById(R.id.inputCode1)
        inpCode2 = findViewById(R.id.inputCode2)
        inpCode3 = findViewById(R.id.inputCode3)
        inpCode4 = findViewById(R.id.inputCode4)
        inpCode5 = findViewById(R.id.inputCode5)
        inpCode6 = findViewById(R.id.inputCode6)

        setupMasterPin()

        val masterPinInitialSetupBackBtn = findViewById<Button>(R.id.masterPinInitialSetup_backBtn)
        masterPinInitialSetupBackBtn.setOnClickListener {
            if ((master_Pin != null) && (lockedAppsList != null)) {
                val toMainMenu = Intent(this,MainMenuActivity::class.java)
                startActivity(toMainMenu)
            } else {
                val toDevNotes = Intent(this, DevNotesActivity::class.java)
                startActivity(toDevNotes)
            }
        }

        val masterPinInitialSetupNextBtn = findViewById<Button>(R.id.masterPinInitialSetup_nextBtn)
        masterPinInitialSetupNextBtn.setOnClickListener(View.OnClickListener{
            if(inpCode1.text.toString().trim().isEmpty() ||
                inpCode2.text.toString().trim().isEmpty() ||
                inpCode3.text.toString().trim().isEmpty() ||
                inpCode4.text.toString().trim().isEmpty() ||
                inpCode5.text.toString().trim().isEmpty() ||
                inpCode6.text.toString().trim().isEmpty()) {
                Toast.makeText(applicationContext, "Please enter valid code", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            masterPin = inpCode1.text.toString() +
                    inpCode2.text.toString() +
                    inpCode3.text.toString() +
                    inpCode4.text.toString() +
                    inpCode5.text.toString() +
                    inpCode6.text.toString()

            for (i in 0..1) {
                Toast.makeText(applicationContext, "Screenshot this for later:\nApp Lock Master PIN: $masterPin", Toast.LENGTH_SHORT).show()
            }

            editor_masterPin.putString("masterPIN", masterPin)
            editor_masterPin.apply()

            if ((master_Pin != null) && (lockedAppsList != null))
            {
                val toMainMenu = Intent(this,MainMenuActivity::class.java)
                startActivity(toMainMenu)
            } else {
                val toMobileApplicationsSelection = Intent(this, MobileApplicationsActivity::class.java)
                startActivity(toMobileApplicationsSelection)
            }
        })
    }

    private fun setupMasterPin() {
        inpCode1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()) {
                    inpCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        inpCode2.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()) {
                    inpCode3.requestFocus()
                }
                else {
                    inpCode1.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        inpCode3.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()) {
                    inpCode4.requestFocus()
                }
                else {
                    inpCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        inpCode4.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()) {
                    inpCode5.requestFocus()
                }
                else {
                    inpCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        inpCode5.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()) {
                    inpCode6.requestFocus()
                }
                else {
                    inpCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
        inpCode6.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()) {

                }
                else {
                    inpCode5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    override fun onBackPressed() {
        return
    }
}