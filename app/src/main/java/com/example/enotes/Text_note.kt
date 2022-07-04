package com.example.enotes

import android.Manifest
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.adapter.AdapterFile_attach
import com.example.enotes.adapter.Adapter_img_attach
import com.example.enotes.databasehelper.DbHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.madrapps.pikolo.ColorPicker
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener
import com.redhoodhan.draw.DrawView
import kotlinx.android.synthetic.main.dialog_draw.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class Text_note : AppCompatActivity() {

    var currentLocation: Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 101

    lateinit var image_Uri: Uri
    lateinit var fileUri: Uri

    private val fileNameList: ArrayList<String>? = null
    private val fileDoneList: List<String>? = null
    private val fileUriList: ArrayList<Uri>? = null

    var fileName: String = ""
    var result: String = ""

    var bg_clr: Int = R.color.white
    var fontclr: Int = R.color.black
    var txtsize: Float = 16.0F
    var font: Int = R.font.roboto_regular

    var my_loc: String = ""
    var addressline: String = ""
    var lat: String = ""
    var lng: String = ""


    var day = 0
    var month = 0
    var years = 0
    var hours = 0
    var minutes = 0
    var returndate = ""
    var returndate2 = ""
    var reminderDateTimeInMilliseconds: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_note)
        val db = DbHelper(this)

        var typeface = ResourcesCompat.getFont(this, font)

        val imageView = findViewById<ImageView>(R.id.image)
        val cardView = findViewById<CardView>(R.id.card2)
        val cardView1 = findViewById<CardView>(R.id.card1)
        val done = findViewById<FloatingActionButton>(R.id.done)
        val content = findViewById<EditText>(R.id.text_context)
        val text_title = findViewById<EditText>(R.id.text_title)
        val currentloctn = findViewById<TextView>(R.id.location)
        val bgcolor = findViewById<ImageButton>(R.id.bgcolor)
        val textfont = findViewById<ImageButton>(R.id.textfont)
        val textSize = findViewById<ImageButton>(R.id.textsize)
        val font_color = findViewById<ImageButton>(R.id.fontcolor)
        val imgUriList = ArrayList<Uri>()
        val imageNameList = ArrayList<String>()
        val img_recyclerview = findViewById<RecyclerView>(R.id.img_attach)
        val img_adapter = Adapter_img_attach(imgUriList, imageNameList, this)
        val alarm_tv = findViewById<TextView>(R.id.alarm_tv)
        val myCalendar: Calendar = Calendar.getInstance()
        val bookmrk = findViewById<ImageView>(R.id.bookmark)
        val mybookmrk = findViewById<ImageButton>(R.id.my_bookmark)
        val my_drawing = findViewById<ImageButton>(R.id.my_sketch)

        val time = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
            //time save hua
            hours = hour
            minutes = minute

            val hourvar = if (hour < 10) "0" + (hour) else (hour).toString() + ""
            // returndate1 = year + "-" + monthVar + "-" + dayOfMonth;
            val minuteVar = if (minute < 10) "0$minute" else minute.toString() + ""
            returndate2 = "$hourvar:$minuteVar"

            // returndate2= "$hour: $minute"
            alarm_tv.setText(returndate + " " + returndate2)

            setAlarm()

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

        alarm_tv.setOnClickListener {
            DatePickerDialog(
                this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()


        }

        content.setTextSize(txtsize)
        text_title.setTypeface(typeface)
        content.setTypeface(typeface)

        var ooo: String = ""
        mybookmrk.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_bookmark, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Select Font")
            val mAlertDialog = mBuilder.show()

            val red = view.findViewById<LinearLayout>(R.id.bookmrk_work)
            red.setOnClickListener {
                mAlertDialog.dismiss()
                bookmrk.setImageResource(R.drawable.bookmark_red)
            }

            val blue = view.findViewById<LinearLayout>(R.id.bookmrk_Travel)
            blue.setOnClickListener() {
                mAlertDialog.dismiss()
                bookmrk.setImageResource(R.drawable.bookmark_blue)
            }

            val green = view.findViewById<LinearLayout>(R.id.bookmrk_personal)
            green.setOnClickListener {
                mAlertDialog.dismiss()
                bookmrk.setImageResource(R.drawable.bookmark_grren)
            }
            val yellow = view.findViewById<LinearLayout>(R.id.bookmrk_life)
            yellow.setOnClickListener {
                mAlertDialog.dismiss()
                bookmrk.setImageResource(R.drawable.bookmark_yellow)
            }
            val orange = view.findViewById<LinearLayout>(R.id.bookmrk_birthday)
            orange.setOnClickListener {
                mAlertDialog.dismiss()
                bookmrk.setImageResource(R.drawable.bookmark_orage)
            }
            val untag = view.findViewById<LinearLayout>(R.id.bookmrk_untag)
            untag.setOnClickListener {
                mAlertDialog.dismiss()
                bookmrk.setImageResource(R.drawable.bookmark_border_24)
            }
        }

        textfont.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_fonts, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Select Font")
            val mAlertDialog = mBuilder.show()

            val comforta_bold = view.findViewById<TextView>(R.id.comfortaa_bold)
            comforta_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.comfortaa_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }

            val comforta_light = view.findViewById<TextView>(R.id.comfortaa_light)
            comforta_light.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.comfortaa_light
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)


            }
            val comforta_meddium = view.findViewById<TextView>(R.id.comfortaa_medium)
            comforta_meddium.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.comfortaa_medium
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val comforta_reg = view.findViewById<TextView>(R.id.comfortaa_reg)
            comforta_reg.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.comfortaa_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val dancing_bold = view.findViewById<TextView>(R.id.dancing_bold)
            dancing_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.dancing_script_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)


            }
            val dancing_med = view.findViewById<TextView>(R.id.dancing_medium)
            dancing_med.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.dancing_script_med
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val dancing_reg = view.findViewById<TextView>(R.id.dancing_reg)
            dancing_reg.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.dancing_script_reg
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val gotham_bold = view.findViewById<TextView>(R.id.gotham_bold)
            gotham_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.gotham_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val gotham_med = view.findViewById<TextView>(R.id.gotham_medium)
            gotham_med.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.comfortaa_medium
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val gotham_italic = view.findViewById<TextView>(R.id.gotham_italic)
            gotham_italic.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.gotham_medium_italic
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val lato_bold = view.findViewById<TextView>(R.id.lato_bold)
            lato_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.lato_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val lato_italic = view.findViewById<TextView>(R.id.lato_italic)
            lato_italic.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.lato_italic
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val lato_reg = view.findViewById<TextView>(R.id.lato_reg)
            lato_reg.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.lato_reg
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val montserrat_bold = view.findViewById<TextView>(R.id.montserrat_bold)
            montserrat_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.montserrat_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val montserrat_italic = view.findViewById<TextView>(R.id.montserrat_italic)
            montserrat_italic.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.montserrat_italic
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val montserrat_reg = view.findViewById<TextView>(R.id.montserrat_regular)
            montserrat_reg.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.montserrat_reg
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val poppins_bold = view.findViewById<TextView>(R.id.poppins_bold)
            poppins_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.poppins_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val poppins_italic = view.findViewById<TextView>(R.id.poppins_italic)
            poppins_italic.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.poppins_italic
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val poppins_reg = view.findViewById<TextView>(R.id.poppins_reg)
            poppins_reg.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.poppins_regular
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val roboto_bold = view.findViewById<TextView>(R.id.roboto_bold)
            roboto_bold.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.roboto_bold
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val roboto_italic = view.findViewById<TextView>(R.id.roboto_italic)
            roboto_italic.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.roboto_italic
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val roboto_reg = view.findViewById<TextView>(R.id.roboto_reg)
            roboto_reg.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.roboto_regular
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val pacifico = view.findViewById<TextView>(R.id.pacifico)
            pacifico.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.pacifico
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val satisfy = view.findViewById<TextView>(R.id.satisfy)
            satisfy.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.satisfy
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val shadows = view.findViewById<TextView>(R.id.shadows)
            shadows.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.shadows_into_light
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }
            val waver = view.findViewById<TextView>(R.id.waver)
            waver.setOnClickListener {
                mAlertDialog.dismiss()
                font = R.font.waver
                typeface = ResourcesCompat.getFont(this, font)
                content.setTypeface(typeface)
                text_title.setTypeface(typeface)

            }

        }

        textSize.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_size, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Select Tet Size")
            val mAlertDialog = mBuilder.show()

            val small = view.findViewById<TextView>(R.id.small)
            val medium = view.findViewById<TextView>(R.id.medium)
            val large = view.findViewById<TextView>(R.id.large)
            small.setOnClickListener {
                mAlertDialog.dismiss()
                txtsize = 16.0f
                content.setTextSize(txtsize)
                text_title.setTextSize(txtsize + 10)
            }
            medium.setOnClickListener {
                mAlertDialog.dismiss()
                txtsize = 22F
                content.setTextSize(txtsize)
                text_title.setTextSize(txtsize + 10)

            }
            large.setOnClickListener {
                mAlertDialog.dismiss()
                txtsize = 32F
                content.setTextSize(txtsize)
                text_title.setTextSize(txtsize + 10)
            }


        }
        font_color.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.color_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Select Font Color")
            val mAlertDialog = mBuilder.show()
            val colorpick = view.findViewById<ColorPicker>(R.id.colorpicker)
            colorpick.setColorSelectionListener(object : SimpleColorSelectionListener() {
                override fun onColorSelected(color: Int) {
                    // Do whatever you want with the color
                    Log.e("color1", color.toString())

                    fontclr = color
                }
            })

            val button = view.findViewById<Button>(R.id.selectcolor)
            button.setOnClickListener {
                mAlertDialog.dismiss()
                text_title.setTextColor(fontclr)
                content.setTextColor(fontclr)
            }
        }
        bgcolor.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.color_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Select Background Color")
            val mAlertDialog = mBuilder.show()

            val colorpick = view.findViewById<ColorPicker>(R.id.colorpicker)
            colorpick.setColorSelectionListener(object : SimpleColorSelectionListener() {
                override fun onColorSelected(color: Int) {
                    // Do whatever you want with the color
                    Log.e("color1", color.toString())

                    bg_clr = color
                }
            })

            val button = view.findViewById<Button>(R.id.selectcolor)
            button.setOnClickListener {
                mAlertDialog.dismiss()
                cardView.setCardBackgroundColor(bg_clr)
                cardView1.setCardBackgroundColor(bg_clr)

            }

        }

        // for file upload
        val fileupload = findViewById<ImageButton>(R.id.fileupload)
        val file_Name = ArrayList<String>()
        val fileUriList = ArrayList<Uri>()
        val file_recyclerview = findViewById<RecyclerView>(R.id.file_attach)
        val file_adapter = AdapterFile_attach(fileUriList, file_Name, this)

        file_recyclerview.adapter = file_adapter
        file_recyclerview.layoutManager = GridLayoutManager(this, 2)

//        file_recyclerview.layoutManager = LinearLayoutManager(this)

//        img_recyclerview.adapter = img_adapter
//        img_recyclerview.layoutManager = LinearLayoutManager(this)


        my_drawing.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_draw, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Draw sketch")
            val mAlertDialog = mBuilder.show()

            val drawingView = view.findViewById<DrawView>(R.id.drawing_view)
            val done = view.findViewById<ImageButton>(R.id.done_btn)
            val undo = view.findViewById<ImageButton>(R.id.undo_btn)
            val redo = view.findViewById<ImageButton>(R.id.redo_btn)
            val delete = view.findViewById<ImageButton>(R.id.delete_btn)

            undo.setOnClickListener {
                drawingView.undo()
            }
            redo.setOnClickListener {
                drawingView.redo()
            }
            delete.setOnClickListener {
                drawingView.clearCanvas(true)
            }
            done.setOnClickListener {
                mAlertDialog.dismiss()
                var myUri = getImageUri(this@Text_note, drawingView.drawing_view.saveAsBitmap())
                //val getit : String =
                myUri?.let {
                    fileUriList.add(it)
                    file_Name.add(it.toString())
                }
                drawingView.isSaveEnabled
                file_adapter.notifyDataSetChanged()
//                val bitmap = drawingView.saveAsBitmap()
//                val uri= Uri.parse(bitmap.toString())

                //   Log.e("getit",uri.toString())


//                val bitmap = Bitmap.createBitmap(drawingView.saveAsBitmap())
//                val stream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100,   jja3                             )
//                val byteArray: ByteArray = stream.toByteArray()
//
//                val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

                //       imageView.setImageURI(myUri)
                //  fileUriList.add(uri)


            }


        }

        fun getFileName(uri: Uri?): String {
            result = null.toString()
            if (uri != null) {
                if (uri.scheme == "content") {
                    val cursor = contentResolver.query(uri, null, null, null, null)
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            result =
                                cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                        }
                    } finally {
                        cursor!!.close()
                    }
                }
            }
            if (result == null) {
                if (uri != null) {
                    result = uri.path.toString()
                }
                val cut: Int = result.lastIndexOf('/')
                if (cut != -1) {
                    result = result.substring(cut + 1)
                }
            }
            return result
        }


// Receiver
        val getFileResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode == RESULT_OK) {
                    Log.e("success3", "succ")
                    if (it.data?.data != null) {

                        fileUri = Uri.parse(it.data!!.data.toString())
                        fileUriList.add(fileUri)
                        fileName = getFileName(fileUri)
                        Log.e("name", fileName)
                        file_Name.add(fileName)
                        file_adapter.notifyDataSetChanged()


                    }

                    if (it.data?.clipData != null) {

                        val totalItemsSelected = it.data!!.clipData!!.itemCount
                        for (i in 0 until totalItemsSelected) {
                            Log.e("success2", "succ")

                            fileUri = it.data!!.clipData!!.getItemAt(i).uri
                            fileUriList.add(fileUri)
                            fileName = getFileName(fileUri)
                            Log.e("name", fileName)
                            file_Name.add(fileName)
                            file_adapter.notifyItemInserted(i)
                            file_adapter.notifyDataSetChanged()

                        }
                    }

                }

            }

        fileupload.setOnClickListener {

            val intent2 = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent2.setType("*/*")
            intent2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            //         intent2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false)
//
            getFileResult.launch(intent2)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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

            val task = fusedLocationProviderClient!!.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location

                    val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                    my_loc = latLng.toString()


                    ooo = my_loc
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude,
                        1
                    )
                    if (addresses.size > 0) {
                        currentloctn.setText(addresses[0].getAddressLine(0))
                        addressline = addresses[0].getAddressLine(0)
                        Log.e("location_inside", addresses[0].adminArea)
                        Log.e("location_inside2", addresses[0].locality)
                        Log.e("location_inside3", addresses[0].countryName)
                        Log.e("location_inside4", addresses[0].getAddressLine(0))
                        Log.e("location_inside5", addresses[0].subAdminArea)
                        Log.e("location_inside6", addresses[0].subLocality)

                    }
                    Log.e("location_inside", ooo)

                    currentloctn.setOnClickListener {
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

        fun fetchLocationSearch(mysearch: String) {

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
            currentloctn.setText(mysearch)
            Log.e("mysrch", mysearch)
            var addressList: List<Address>? = null

            if (mysearch != null || mysearch != "") {


                val geocoder = Geocoder(this)
                try {
                    addressList = geocoder.getFromLocationName(mysearch, 1)
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


                currentloctn.setOnClickListener {
                    val t = Toast.makeText(this, my_loc, Toast.LENGTH_SHORT)
                    t.show()
                    val strUri =
                        "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + addressline + ")"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

                    intent.setClassName(
                        "com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"
                    )

                    startActivity(intent)
                }

            }
        }

        findViewById<ImageButton>(R.id.my_location).setOnClickListener {
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
                val mysearch: String = search_location.text.toString()
                Log.e("mysrch1", mysearch)

                fetchLocationSearch(mysearch)

            }

        }


        done.setOnClickListener {
            val title: String = text_title.text.toString()
            val contnt: String = content.text.toString()

            Log.e("clr bgg", bg_clr.toString() + " ")
            Log.e("clr f", fontclr.toString() + " ")
            Log.e("check2", returndate.toString() + " " + returndate2)

            db.addNotes(
                text_title.text.toString(),
                content.text.toString(),
                txtsize.toString(),
                font.toString(),
                fontclr.toString(),
                bg_clr.toString(),
                addressline,
                file_Name
            )

            finish()
        }

    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun setAlarm() {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent: Intent
        val _id = System.currentTimeMillis().toInt()

        val pendingIntent: PendingIntent
        myIntent = Intent(this, BroadcastService::class.java)
        myIntent.putExtra("time", returndate2)

        pendingIntent =
            PendingIntent.getBroadcast(this, _id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val setdate: Calendar
        setdate = Calendar.getInstance()

        setdate.setTimeInMillis(System.currentTimeMillis());
//.Set(Year, Month, Day, Hour, Minutes, Seconds);
        setdate.set(years, month, day, hours, minutes, 0)

        Log.e("success", day.toString())

        reminderDateTimeInMilliseconds = setdate.getTimeInMillis()
        Log.e("success", reminderDateTimeInMilliseconds.toString())

        manager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            (reminderDateTimeInMilliseconds),
            pendingIntent
        )
    }

    fun getBitMap(encodedString: String?): Bitmap? {
        return try {
            val decoder: java.util.Base64.Decoder = java.util.Base64.getDecoder()
            val decoded = String(decoder.decode(encodedString))


            val decodedString: ByteArray = decoded.toByteArray()
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

}