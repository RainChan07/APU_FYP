package com.example.ALMD_AppLockforMobileDevice.Security

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ALMD_AppLockforMobileDevice.AppLockApplication.MainMenuActivity
import com.example.ALMD_AppLockforMobileDevice.R

class EnterPINActivity : AppCompatActivity() {

    private lateinit var mPCode1: EditText
    private lateinit var mPCode2: EditText
    private lateinit var mPCode3: EditText
    private lateinit var mPCode4: EditText
    private lateinit var mPCode5: EditText
    private lateinit var mPCode6: EditText

    private lateinit var masterPin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_pin)

        val sharedPref_masterPin = getSharedPreferences("masterPin", MODE_PRIVATE)
        val master_Pin: String? = sharedPref_masterPin.getString("masterPIN", null)

        val sharedPref_unlockingApp = getSharedPreferences("unlockingApp", MODE_PRIVATE)
        val editor_unlockingApp = sharedPref_unlockingApp.edit()
        val unlockingApp: String? = sharedPref_unlockingApp.getString("unlockingApp", null)

        val sharedPref_AU = getSharedPreferences("afterUnlock", MODE_PRIVATE)
        val editor_AU = sharedPref_AU.edit()
        val afterUnlock: String? = sharedPref_AU.getString("afterUnlock", null)

        mPCode1 = findViewById(R.id.mPCode1)
        mPCode2 = findViewById(R.id.mPCode2)
        mPCode3 = findViewById(R.id.mPCode3)
        mPCode4 = findViewById(R.id.mPCode4)
        mPCode5 = findViewById(R.id.mPCode5)
        mPCode6 = findViewById(R.id.mPCode6)

        verifyMasterPIN()

        val masterPinVerificationNextBtn = findViewById<Button>(R.id.enterPIN_confirmBtn)
        masterPinVerificationNextBtn.setOnClickListener(View.OnClickListener {
            if (mPCode1.text.toString().trim().isEmpty() ||
                mPCode2.text.toString().trim().isEmpty() ||
                mPCode3.text.toString().trim().isEmpty() ||
                mPCode4.text.toString().trim().isEmpty() ||
                mPCode5.text.toString().trim().isEmpty() ||
                mPCode6.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(applicationContext, "Please enter Master PIN", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }

            masterPin = mPCode1.text.toString() +
                    mPCode2.text.toString() +
                    mPCode3.text.toString() +
                    mPCode4.text.toString() +
                    mPCode5.text.toString() +
                    mPCode6.text.toString()

            if (masterPin == master_Pin) {
                Toast.makeText(applicationContext, "User Verified", Toast.LENGTH_SHORT).show()
                if (unlockingApp != null) {
                    editor_AU.putString("afterUnlock", "1")
                    editor_AU.apply()
                    val launchLockedApp = packageManager.getLaunchIntentForPackage(unlockingApp)
                    startActivity(launchLockedApp)
                } else {
                    val toMainMenuActivity = Intent(this, MainMenuActivity::class.java)
                    startActivity(toMainMenuActivity)
                }
            } else {
                Toast.makeText(applicationContext, "Incorrect Master PIN", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun verifyMasterPIN() {
        mPCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    mPCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        mPCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    mPCode3.requestFocus()
                } else {
                    mPCode1.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        mPCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    mPCode4.requestFocus()
                } else {
                    mPCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        mPCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    mPCode5.requestFocus()
                } else {
                    mPCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        mPCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    mPCode6.requestFocus()
                } else {
                    mPCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        mPCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {

                } else {
                    mPCode5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onBackPressed() {
        return
    }
}