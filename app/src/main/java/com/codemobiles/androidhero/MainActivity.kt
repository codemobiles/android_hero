package com.codemobiles.androidhero

import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPref()
        setupEventWidget()
    }

    private fun setupEventWidget() {
        username_edittext.setText(Prefs.getString(PREF_USERNAME, ""))
        password_edittext.setText(Prefs.getString(PREF_PASSWORD, ""))

        login_button.setOnClickListener {
            val username = username_edittext.text.toString()
            val password: String = password_edittext.text.toString()

            Toast.makeText(applicationContext, "u: $username, p: $password", Toast.LENGTH_LONG)
                .show()

            Prefs.putString(PREF_USERNAME, username)
            Prefs.putString(PREF_PASSWORD, password)
        }

        gmail_button.setOnClickListener {
            Toast.makeText(applicationContext, "GMail", Toast.LENGTH_LONG).show()
        }

        facebook_button.setOnClickListener {
            Toast.makeText(applicationContext, "Facebook", Toast.LENGTH_LONG).show()
        }
    }

    private fun initPref() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

}