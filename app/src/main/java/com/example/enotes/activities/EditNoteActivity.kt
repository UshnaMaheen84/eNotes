package com.example.enotes.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
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
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.R
import com.example.enotes.adapter.Adapter_img_attach
import com.example.enotes.databasehelper.DbHelper
import com.example.enotes.helper.MyHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_text_note.*
import kotlinx.android.synthetic.main.activity_view_notes.*
import kotlinx.android.synthetic.main.activity_view_notes.text_context
import kotlinx.android.synthetic.main.activity_view_notes.text_title
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {


    val REQUEST_CODE = 101
    var my_loc: String = ""
    var addressline: String = ""
    var lat: String = ""
    var lng: String = ""
    var currentLocation: Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var updatedAddress = ""
    var alarmDate = "add reminder"
    var day = 0
    var month = 0
    var years = 0
    var hours = 0
    var minutes = 0
    var returndate = ""
    var returndate2 = ""
    lateinit var db: DbHelper
    var fabVisible = false


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_notes)
        val db = DbHelper(this)


        val addFAB = findViewById<FloatingActionButton>(R.id.idFABAdd)

        val cardView = findViewById<CardView>(R.id.card2)
        val cardView1 = findViewById<CardView>(R.id.card1)
        val text_title = findViewById<EditText>(R.id.text_title)
        val tv_currentloctn = findViewById<TextView>(R.id.location)

        val bgcolor = findViewById<FloatingActionButton>(R.id.bgcolor)
        val textfont = findViewById<FloatingActionButton>(R.id.textfont)
        val textSize = findViewById<FloatingActionButton>(R.id.textsize)
        val font_color = findViewById<FloatingActionButton>(R.id.fontcolor)
        val imgUriList = ArrayList<Uri>()
        val imageNameList = ArrayList<String>()
        val img_recyclerview = findViewById<RecyclerView>(R.id.img_attach)
        val img_adapter = Adapter_img_attach(imgUriList, imageNameList, this)
        val alarm_tv = findViewById<TextView>(R.id.alarm_tv)
        val bookmrk = findViewById<ImageView>(R.id.bookmark)
        val mybookmrk = findViewById<FloatingActionButton>(R.id.my_bookmark)
        val my_drawing = findViewById<FloatingActionButton>(R.id.my_sketch)
        val fileupload = findViewById<FloatingActionButton>(R.id.img_upload)

        fabVisible = false

        val tilte = intent.extras!!.getString("title")
        val content = intent.extras!!.getString("content")
        val textsize = intent.extras!!.getString("textsize")
        val textclr = intent.extras!!.getString("textclr")
        val bgclr = intent.extras!!.getString("bg")
        val reminder_date = intent.extras!!.getString("reminder_date")
        val myCalendar: Calendar = Calendar.getInstance()

        val time = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
            //time save hua
            hours = hour
            minutes = minute

            val hourvar = if (hour < 10) "0" + (hour) else (hour).toString() + ""
            // returndate1 = year + "-" + monthVar + "-" + dayOfMonth;
            val minuteVar = if (minute < 10) "0$minute" else minute.toString() + ""
            returndate2 = "$hourvar:$minuteVar"

            alarmDate = returndate + " " + returndate2
            // returndate2= "$hour: $minute"
            view_reminders.setText(alarmDate)


        }

        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                //yahn calender se date mili
                day = dayOfMonth
                month = monthOfYear
                years = year

                val monthVar =
                    if (monthOfYear < 10) "0" + (monthOfYear + 1) else (monthOfYear + 1).toString() + ""
                // returndate1 = year + "-" + monthVar + "-" + dayOfMonth;
                val dateVar = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString() + ""
                returndate = "$dateVar-$monthVar-$year"
                //textview mai date return ho k save hui
                //jese hi calender bnd hoga timepicker show hoga
                TimePickerDialog(
                    this, time,
                    myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false
                ).show()

            }

        Log.e("reminder_date", reminder_date.toString())
        if (reminder_date == "add reminder") {
            view_reminders.setText(" ")
        } else {
            view_reminders.setText("Reminder set for " + reminder_date)
        }

        view_reminders.setOnClickListener {
            DatePickerDialog(
                this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()


        }

        view_reminders.setOnLongClickListener {
            //cancelAlarm()

            val dialog = AlertDialog.Builder(this)
            dialog.setMessage("Do you want to delete this image?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->

                alarmDate = "add reminder"
                view_reminders.setText(alarmDate)
            })

            dialog.show()

            true
        }


        val address = intent.extras!!.getString("address")
        viewAddress.setText(address)
        viewAddress.setOnClickListener {
            fetchLocationSearch(address)

        }
        // to update notes
        update.setOnClickListener {
            update.visibility = View.GONE
            fab_layout_two.visibility = View.VISIBLE

        }

        done_two.setOnClickListener {
            view_done.visibility = View.VISIBLE
            fab_layout_two.visibility = View.GONE
        }

        idFABAdd_two.setOnClickListener {

            if (!fabVisible) {
                my_bookmark_two.visibility = View.VISIBLE
                textsize_two.visibility = View.VISIBLE
                textfont_two.visibility = View.VISIBLE
                fontcolor_two.visibility = View.VISIBLE
                bgcolor_two.visibility = View.VISIBLE
                my_sketch_two.visibility = View.VISIBLE
                img_upload_two.visibility = View.VISIBLE
                my_location_two.visibility = View.VISIBLE
                done_two.visibility = View.GONE
                idFABAdd_two.setImageResource(R.drawable.ic_close)
                fabVisible = true

            } else {
                hideFabs()

            }

        }


        view_done.setOnClickListener {
            /**
             * Update Note work here
             */
            if (validateTextDescription()) {
                val title: String = text_title.text.toString()
                val contnt: String = text_context.text.toString()

                finish()
            }
        }

        edit_address.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.alertdialog_cocation, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
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
                val mysearch: String = search_location.text.toString()
                Log.e("mysrch1", mysearch)

                fetchLocationSearch(mysearch)

            }

        }

        // to set fonts
        showFonts()


        Log.e("inside c", textclr.toString())
        val clr: Int = R.color.black
        val bgclrr: Int = R.color.bbbb

        text_title.setTextColor(clr)
        text_context.setTextColor(clr)

        text_title.setText(tilte.toString())
        text_context.setText(content.toString())

        cardView1.setCardBackgroundColor(bgclrr)
        cardView.setCardBackgroundColor(bgclrr)

        if (bgclr != null) {
            cardView1.setCardBackgroundColor(bgclr.toInt())
            cardView.setCardBackgroundColor(bgclr.toInt())

            if (bgclr == "2131035097") {
                Log.e("inside", " backgrnd")

                cardView1.setCardBackgroundColor(Color.WHITE)
                cardView.setCardBackgroundColor(Color.WHITE)

            }
        }


        if (textclr != null) {
            text_title.setTextColor(textclr.toInt())
            text_context.setTextColor(textclr.toInt())
            if (textclr == "2131034145") {

                text_title.setTextColor(clr)
                text_context.setTextColor(clr)
            }
        }


        if (textsize != null) {
            text_context.setTextSize(textsize.toFloat())
            text_title.setTextSize((textsize.toFloat() + 4))

        }


    }

    private fun validateTextDescription(): Boolean {
        if (text_title.text.toString().length == 0) {
            MyHelper.showToast(this, "Notes title is empty")
            return false
        } else if (text_context.text.toString().length == 0) {
            MyHelper.showToast(this, "Notes description is empty")
            return false

        }
        return true
    }

    private fun hideFabs() {

        textsize_two.visibility = View.GONE
        my_bookmark_two.visibility = View.GONE
        my_sketch_two.visibility = View.GONE
        fontcolor_two.visibility = View.GONE
        textfont_two.visibility = View.GONE
        fontcolor_two.visibility = View.GONE
        bgcolor_two.visibility = View.GONE
        my_location_two.visibility = View.GONE
        img_upload_two.visibility = View.GONE
        done_two.visibility = View.VISIBLE
        idFABAdd_two.setImageResource(R.drawable.ic_add)
        fabVisible = false

    }

    fun fetchLocationSearch(address: String?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
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
                lat = address.latitude.toString()
                lng = address.longitude.toString()

            } catch (d: java.lang.Exception) {
                Log.e("error", d.message.toString())
            }


            updatedAddress = address.toString()
            viewAddress.setText(address)
            viewAddress.setOnClickListener {
                val strUri =
                    "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + address + ")"
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
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                my_loc = latLng.toString()


                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address> = geocoder.getFromLocation(
                    currentLocation!!.latitude,
                    currentLocation!!.longitude,
                    1
                )
                if (addresses.size > 0) {
                    viewAddress.setText(addresses[0].getAddressLine(0))
                    addressline = addresses[0].getAddressLine(0)
                    Log.e("location_inside", addresses[0].adminArea)
                    Log.e("location_inside2", addresses[0].locality)
                    Log.e("location_inside3", addresses[0].countryName)
                    Log.e("location_inside4", addresses[0].getAddressLine(0))
                    Log.e("location_inside5", addresses[0].subAdminArea)
                    Log.e("location_inside6", addresses[0].subLocality)

                }

                updatedAddress = addressline
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