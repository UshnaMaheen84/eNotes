package com.example.enotes

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_open_image.*
import java.io.ByteArrayInputStream
import java.io.InputStream


class OpenImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_image)

        val img_uri = intent.extras!!.getString("ImageUri")
        val uri: Uri= Uri.parse(img_uri)

//
//        val decodedString: ByteArray = Base64.decode(img_uri, Base64.NO_WRAP)
//        val input: InputStream = ByteArrayInputStream(decodedString)
//        val ext_pic = BitmapFactory.decodeStream(input)
        imageview.setImageURI(uri)

        Log.e("myuri", uri.toString())
        go_back.setOnClickListener {
            finish()
        }

    }
}