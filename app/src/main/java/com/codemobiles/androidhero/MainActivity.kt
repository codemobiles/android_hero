package com.codemobiles.androidhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener {
            val username = username_edittext.text.toString()
            val password: String = password_edittext.text.toString()

            Toast.makeText(applicationContext, "u: $username, p: $password", Toast.LENGTH_LONG)
                .show()
        }

        gmail_button.setOnClickListener {
            Toast.makeText(applicationContext, "GMail", Toast.LENGTH_LONG).show()
        }

        facebook_button.setOnClickListener {
            Toast.makeText(applicationContext, "Facebook", Toast.LENGTH_LONG).show()
        }
    }

}