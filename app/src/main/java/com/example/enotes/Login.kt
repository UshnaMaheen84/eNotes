package com.example.enotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val login_btn = findViewById<Button>(R.id.login_btn)
        val forget_pswrd = findViewById<TextView>(R.id.forget_password)
        val dont_have_accnt = findViewById<TextView>( R.id.signup)

        login_btn.setOnClickListener {
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        dont_have_accnt.setOnClickListener{
                val intent = Intent(this@Login, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}