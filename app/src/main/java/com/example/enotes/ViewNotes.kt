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
import kotlinx.android.synthetic.main.activity_view_notes.*

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
        val fonts = intent.extras!!.getString("font")
        val bgclr = intent.extras!!.getString("bg")
        val address= intent.extras!!.getString("address")

        viewAddress.setText(address)

        val myfont: Int = R.font.roboto_regular

       var typeface = ResourcesCompat.getFont(this, myfont)
        text_title.setTypeface(typeface)
        mycontent.setTypeface(typeface)
        if (fonts == "waver") {
            typeface = ResourcesCompat.getFont(this, R.font.waver)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "shadows_into_light") {
            typeface = ResourcesCompat.getFont(this, R.font.shadows_into_light)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "satisfy") {
            typeface = ResourcesCompat.getFont(this, R.font.satisfy)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "pacifico") {
            typeface = ResourcesCompat.getFont(this, R.font.pacifico)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "roboto_regular") {
            typeface = ResourcesCompat.getFont(this, R.font.roboto_regular)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "roboto_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.roboto_italic)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "roboto_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "poppins_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.poppins_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "poppins_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.poppins_italic)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "poppins_regular") {
            typeface = ResourcesCompat.getFont(this, R.font.poppins_regular)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "montserrat_reg") {
            typeface = ResourcesCompat.getFont(this, R.font.montserrat_reg)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "montserrat_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.montserrat_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "montserrat_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.montserrat_italic)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "lato_reg") {
            typeface = ResourcesCompat.getFont(this, R.font.lato_reg)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "lato_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.lato_italic)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "lato_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.lato_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "gotham_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.gotham_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "gotham_medium_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.gotham_medium_italic)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }

     if (fonts == "gotham_medium") {
            typeface = ResourcesCompat.getFont(this, R.font.gotham_medium)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "dancing_script_reg") {
            typeface = ResourcesCompat.getFont(this, R.font.dancing_script_reg)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "dancing_script_med") {
            typeface = ResourcesCompat.getFont(this, R.font.dancing_script_med)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }

     if (fonts == "dancing_script_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.dancing_script_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "comfortaa_medium") {
            typeface = ResourcesCompat.getFont(this, R.font.comfortaa_medium)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "comfortaa_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.comfortaa_bold)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }
        if (fonts == "comfortaa_light") {
            typeface = ResourcesCompat.getFont(this, R.font.comfortaa_light)
            text_title.setTypeface(typeface)
            mycontent.setTypeface(typeface)

        }

        Log.e("inside c",textclr.toString())
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

            if (bgclr=="2131035097"){
                Log.e("inside"," backgrnd")

                cardView1.setCardBackgroundColor(Color.WHITE)
                cardView.setCardBackgroundColor(Color.WHITE)

            }
        }


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
    }
}