package com.example.enotes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ViewNotes : AppCompatActivity() {



    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)
        val mycontent = findViewById<EditText>(R.id.text_context)
        val text_title = findViewById<EditText>(R.id.text_title)
        val bgcolor = findViewById<ImageButton>(R.id.bgcolor)
        val textfont = findViewById<ImageButton>(R.id.textfont)
        val textSize = findViewById<ImageButton>(R.id.textsize)
        val font_color = findViewById<ImageButton>(R.id.fontcolor)
        val cardView = findViewById<CardView>(R.id.card2)
        val cardView1 = findViewById<CardView>(R.id.card1)

        val tilte = intent.extras!!.getString("title")
        val content = intent.extras!!.getString("content")
        val textsize = intent.extras!!.getString("textsize")
        val textclr = intent.extras!!.getString("textclr")
        val fonts = intent.extras!!.getInt("font")
        val bgclr = intent.extras!!.getString("bg")


        val myfont: Int = R.font.roboto_regular

       // var typeface = ResourcesCompat.getFont(this, fonts)

        Log.e("inside c",bgclr.toString())
        val clr: Int =R.color.black
        val bgclrr: Int =R.color.bbbb

        text_title.setTextColor(clr)
        mycontent.setTextColor(clr)

        text_title.setText(tilte.toString())
        mycontent.setText(content.toString())




            cardView1.setCardBackgroundColor(bgclrr)
            cardView.setCardBackgroundColor(bgclrr)

        if (bgclr != null) {
            cardView1.setCardBackgroundColor(bgclr.toInt())
            cardView.setCardBackgroundColor(bgclr.toInt())

            if (bgclr=="2131035092"){
                Log.e("inside"," backgrnd")

                cardView1.setCardBackgroundColor(Color.WHITE)
                cardView.setCardBackgroundColor(Color.WHITE)

            }
        }





//            if (bgclr == null) {
//                Log.e("inside","no backgrnd")
//                cardView1.backgroundTintList(ContextCompat.getColor(this, R.color.white));
//
//
//            }



        if (textclr != null) {
           text_title.setTextColor(textclr.toInt())
           mycontent.setTextColor(textclr.toInt())
            if (textclr == "2131034145") {

                text_title.setTextColor(clr)
                mycontent.setTextColor(clr)
            }
        }


        if (textsize != null) {
            mycontent.setTextSize(textsize.toFloat())
            text_title.setTextSize((textsize.toFloat()+4))

        }

//
//        text_title.setTypeface(typeface)
//        mycontent.setTypeface(typeface)
    }
}