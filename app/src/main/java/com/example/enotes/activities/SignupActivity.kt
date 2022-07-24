package com.example.enotes.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.enotes.R
import com.example.enotes.api.ApiClientSignupLogin
import com.example.enotes.models.request.RegisterRequest
import com.example.enotes.models.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

       val full_name = findViewById<EditText>(R.id.full_name)
       val email = findViewById<EditText>(R.id.email_address)
       val paswrd = findViewById<EditText>(R.id.password_signup)
      val  confirm = findViewById<EditText>(R.id.password_confirm)
      val  signup_btn = findViewById<Button>(R.id.create)
      val  login = findViewById<TextView>(R.id.login)



        signup_btn.setOnClickListener {

              val userEmail = email.text.toString()
            val password = paswrd.text.toString()
            val name= full_name.text.toString()
            val confirm_pass= confirm.text.toString()


            registerUser(name,userEmail, password)

        }

        login.setOnClickListener {
            val intent = Intent(this, NoteListActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser(name: String, email: String, password: String) {
        val registerRequest = RegisterRequest(name,email,password)
        val apiCall = ApiClientSignupLogin.getApiService().registerUser(registerRequest)
        apiCall.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(applicationContext,"this is toast message", Toast.LENGTH_LONG).show()

                    val intent = Intent(this@SignupActivity, NoteListActivity::class.java)
            startActivity(intent)
            finish()

                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(applicationContext,""+t.localizedMessage, Toast.LENGTH_LONG).show()
                Log.e("abc",""+t.localizedMessage)
            }


        })
    }
}

