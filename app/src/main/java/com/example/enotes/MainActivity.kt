package com.example.enotes

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.adapter.All_notes_adapter
import com.example.enotes.databasehelper.DbHelper
import com.example.enotes.models.create_note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var adapter: All_notes_adapter
    lateinit var notesList: ArrayList<create_note>


    fun getAllNotesFromDatabase() {
        val db = DbHelper(this)
        val show_notes = findViewById<RecyclerView>(R.id.show_notes)
        show_notes.layoutManager= GridLayoutManager(this,2)

        notesList = ArrayList()
        notesList.addAll(db.viewnote())
        adapter = All_notes_adapter(notesList, this)
        show_notes.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = DbHelper(this)
        val notesList : ArrayList<create_note> = ArrayList()

        notesList.addAll(db.viewnote())


//        val show_notes = findViewById<RecyclerView>(R.id.show_notes)
//        val adapter = All_notes_adapter(notesList,this)
//        show_notes.adapter = adapter
//        show_notes.layoutManager= GridLayoutManager(this,2)
//        adapter.notifyDataSetChanged()



        val add_notes = findViewById<FloatingActionButton>(R.id.add_notes)
        add_notes.setOnClickListener{

            val intent = Intent(this, Text_note::class.java)
            startActivity(intent)

        }




        val dehaze = findViewById<ImageView>(R.id.dehaze)

        val drawerlayout = findViewById<DrawerLayout>(R.id.my_drawer_layout)
        val navView = findViewById<NavigationView>(R.id.navview)

        dehaze.setOnClickListener {
            drawerlayout.openDrawer(GravityCompat.START)
        }


        toggle= ActionBarDrawerToggle(this,drawerlayout,R.string.open,R.string.close)

        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_allnotes ->
                    Toast.makeText(applicationContext,"all notes",Toast.LENGTH_SHORT).show()


            }
            true
            when(it.itemId){
                R.id.nav_reminders ->
                    Toast.makeText(applicationContext,"all reminders",Toast.LENGTH_SHORT).show()


            }
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onResume() {
        super.onResume()

        getAllNotesFromDatabase()
    }
}