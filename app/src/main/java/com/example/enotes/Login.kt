package com.example.enotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.enotes.api.ApiClientSignupLogin
import com.example.enotes.models.request.LoginRequest
import com.example.enotes.models.response.LoginResponse
import com.example.enotes.models.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val login_btn = findViewById<Button>(R.id.login_btn)
        val dont_have_accnt = findViewById<TextView>(R.id.signup)

        login_btn.setOnClickListener {

            val et_email = email.text.toString()
            val et_pass = password.text.toString()

            if (et_email.isNotEmpty() && et_pass.isNotEmpty()) {
                loginUser(et_email, et_pass)
            }
        }

        dont_have_accnt.setOnClickListener {
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser(email: String, password: String) {

        val loginRequest = LoginRequest(email, password)
        val apiCall = ApiClientSignupLogin.getApiService().loninUser(loginRequest)
        apiCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "this is toast message", Toast.LENGTH_LONG)
                        .show()
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "" + t.localizedMessage, Toast.LENGTH_LONG)
                    .show()
                Log.e("abc", "" + t.localizedMessage)
            }
        })
    }
}