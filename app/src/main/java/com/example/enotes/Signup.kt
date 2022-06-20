package com.example.enotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

       val full_name = findViewById<EditText>(R.id.full_name)
       val paswrd = findViewById<EditText>(R.id.password_signup)
      val  confirm = findViewById<EditText>(R.id.password_confirm)
      val  signup_btn = findViewById<Button>(R.id.create)
      val  login = findViewById<TextView>(R.id.login)


        signup_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

