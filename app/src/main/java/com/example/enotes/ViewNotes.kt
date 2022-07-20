package com.example.enotes

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_view_notes.*
import java.io.IOException
import java.util.*

class ViewNotes : AppCompatActivity() {


    val REQUEST_CODE = 101
    var my_loc :String=""
    var addressline :String=""
    var lat: String=""
    var lng:String=""
    var currentLocation : Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var updatedAddress= ""

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)
        val text_title = findViewById<EditText>(R.id.text_title)
        val cardView = findViewById<CardView>(R.id.card2)
        val cardView1 = findViewById<CardView>(R.id.card1)
        val mycontent = findViewById<EditText>(R.id.text_context)

        val tilte = intent.extras!!.getString("title")
        val content = intent.extras!!.getString("content")
        val textsize = intent.extras!!.getString("textsize")
        val textclr = intent.extras!!.getString("textclr")
        val bgclr = intent.extras!!.getString("bg")
        val reminder_date= intent.extras!!.getString("reminder_date")

        Log.e("reminder_date",reminder_date.toString())
        if (reminder_date=="add reminder"){
            view_reminders.setText(" ")
        }
        else{
        view_reminders.setText("Reminder set for "+reminder_date)}
        val address= intent.extras!!.getString("address")
        viewAddress.setText(address)
        viewAddress.setOnClickListener {
            fetchLocationSearch(address)

        }
// to update notes
        update.setOnClickListener {
            update.visibility = View.GONE
            view_done.visibility= View.VISIBLE

            text_title.isFocusableInTouchMode =true
            text_context.isFocusableInTouchMode= true

     edit_address.visibility= View.VISIBLE
        }

        view_done.setOnClickListener {
            Toast.makeText(this, "updated" +updatedAddress, Toast.LENGTH_SHORT).show()

            finish()
        }

        edit_address.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.alertdialog_cocation, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Search Location")
            val mAlertDialog = mBuilder.show()
            val use_current_location = view.findViewById<TextView>(R.id.my_current_location)
            val search_location = view.findViewById<EditText>(R.id.seach_loc)
            val search = view.findViewById<Button>(R.id.seach_btn)

            use_current_location.setOnClickListener {
                mAlertDialog.dismiss()
                fetchLocation()
            }

            search.setOnClickListener {
                mAlertDialog.dismiss()
                val mysearch: String= search_location.text.toString()
                Log.e("mysrch1",mysearch)

                fetchLocationSearch(mysearch)

            }

        }
// to set fonts
        showFonts()


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
    fun fetchLocationSearch(address: String?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }
        var addressList: List<Address>? = null

        if (address != null || address != "") {



            val geocoder = Geocoder(this)
            try {
                addressList = geocoder.getFromLocationName(address, 1)
            } catch (e: IOException) {
                Log.e("error1", e.message.toString())
            }
            try {
                val address = addressList!![0]
                val latLng = LatLng(address.latitude, address.longitude)
                my_loc = latLng.toString()
                lat= address.latitude.toString()
                lng= address.longitude.toString()

            } catch (d: java.lang.Exception) {
                Log.e("error", d.message.toString())
            }


            updatedAddress= address.toString()
            viewAddress.setText(address)
            viewAddress.setOnClickListener {
                val strUri =
                    "http://maps.google.com/maps?q=loc:" + lat+ "," + lng+ " (" + address + ")"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

                intent.setClassName(
                    "com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity"
                )

                startActivity(intent)
            }

        }


    }

    private fun showFonts() {
        val fonts = intent.extras!!.getString("font")
        val myfont: Int = R.font.roboto_regular

        var typeface = ResourcesCompat.getFont(this, myfont)
        text_title.setTypeface(typeface)
        text_context.setTypeface(typeface)
        if (fonts == "waver") {
            typeface = ResourcesCompat.getFont(this, R.font.waver)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "shadows_into_light") {
            typeface = ResourcesCompat.getFont(this, R.font.shadows_into_light)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "satisfy") {
            typeface = ResourcesCompat.getFont(this, R.font.satisfy)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "pacifico") {
            typeface = ResourcesCompat.getFont(this, R.font.pacifico)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "roboto_regular") {
            typeface = ResourcesCompat.getFont(this, R.font.roboto_regular)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "roboto_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.roboto_italic)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "roboto_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "poppins_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.poppins_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "poppins_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.poppins_italic)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "poppins_regular") {
            typeface = ResourcesCompat.getFont(this, R.font.poppins_regular)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "montserrat_reg") {
            typeface = ResourcesCompat.getFont(this, R.font.montserrat_reg)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "montserrat_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.montserrat_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "montserrat_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.montserrat_italic)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "lato_reg") {
            typeface = ResourcesCompat.getFont(this, R.font.lato_reg)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "lato_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.lato_italic)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "lato_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.lato_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "gotham_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.gotham_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "gotham_medium_italic") {
            typeface = ResourcesCompat.getFont(this, R.font.gotham_medium_italic)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }

        if (fonts == "gotham_medium") {
            typeface = ResourcesCompat.getFont(this, R.font.gotham_medium)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "dancing_script_reg") {
            typeface = ResourcesCompat.getFont(this, R.font.dancing_script_reg)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "dancing_script_med") {
            typeface = ResourcesCompat.getFont(this, R.font.dancing_script_med)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }

        if (fonts == "dancing_script_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.dancing_script_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "comfortaa_medium") {
            typeface = ResourcesCompat.getFont(this, R.font.comfortaa_medium)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "comfortaa_bold") {
            typeface = ResourcesCompat.getFont(this, R.font.comfortaa_bold)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }
        if (fonts == "comfortaa_light") {
            typeface = ResourcesCompat.getFont(this, R.font.comfortaa_light)
            text_title.setTypeface(typeface)
            text_context.setTypeface(typeface)

        }

    }

     fun fetchLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location

                val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                my_loc = latLng.toString()


                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address> = geocoder.getFromLocation(currentLocation!!.latitude, currentLocation!!.longitude, 1)
                if (addresses.size > 0) {
                    viewAddress.setText(addresses[0].getAddressLine(0))
                    addressline= addresses[0].getAddressLine(0)
                    Log.e("location_inside",addresses[0].adminArea)
                    Log.e("location_inside2",addresses[0].locality)
                    Log.e("location_inside3",addresses[0].countryName)
                    Log.e("location_inside4",addresses[0].getAddressLine(0))
                    Log.e("location_inside5",addresses[0].subAdminArea)
                    Log.e("location_inside6",addresses[0].subLocality)

                }

                updatedAddress= addressline
                viewAddress.setText(addressline)
                viewAddress.setOnClickListener {
                    val t = Toast.makeText(this, my_loc, Toast.LENGTH_SHORT)
                    t.show()
                    val strUri =
                        "http://maps.google.com/maps?q=loc:" + currentLocation!!.latitude.toString() + "," + currentLocation!!.longitude.toString() + " (" + addressline + ")"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

                    intent.setClassName(
                        "com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"
                    )

                    startActivity(intent)
                }
    }


}
     }
}