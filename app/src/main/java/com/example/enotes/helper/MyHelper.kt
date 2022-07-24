package com.example.enotes.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.enotes.R

class MyHelper {

    companion object {
        fun changeActivity(from: Context, to: Activity) {
            from.startActivity(Intent(from, to::class.java))
            (from as Activity).overridePendingTransition(R.anim.slidein, R.anim.slideout)
//            to.overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }

        fun showToast(context: Context, message: String) {
            (context as AppCompatActivity)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }
}