package com.codemobiles.androidhero

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityMainBinding
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPref()
        setupEventWidget()
    }

    private fun setupEventWidget() {
        binding.usernameEdittext.setText(Prefs.getString(PREF_USERNAME, ""))
        binding.passwordEdittext.setText(Prefs.getString(PREF_PASSWORD, ""))

        binding.loginButton.setOnClickListener {
            val username = username_edittext.text.toString()
            val password: String = password_edittext.text.toString()

            if(username == "admin@gmail.com" && password == "12345678"){
                Prefs.putString(PREF_USERNAME, username)
                Prefs.putString(PREF_PASSWORD, password)

                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }

        binding.gmailButton.setOnClickListener {
            Toast.makeText(applicationContext, "GMail", Toast.LENGTH_LONG).show()
        }

        binding.facebookButton.setOnClickListener {
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