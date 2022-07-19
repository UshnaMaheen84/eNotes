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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var adapter: All_notes_adapter
    lateinit var notesList: ArrayList<create_note>

    fun getAllNotesFromDatabase() {
        val db = DbHelper(this)
        notesList = ArrayList()
        notesList.addAll(db.viewnote())
        adapter = All_notes_adapter(notesList, this)
        show_notes.adapter = adapter

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show_notes.layoutManager = GridLayoutManager(this, 2)

        add_notes.setOnClickListener {

            val intent = Intent(this, Text_note::class.java)
            startActivity(intent)

        }


        dehaze.setOnClickListener {
            my_drawer_layout.openDrawer(GravityCompat.START)
        }


        toggle = ActionBarDrawerToggle(this, my_drawer_layout, R.string.open, R.string.close)

        my_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navview.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_allnotes ->
                    Toast.makeText(applicationContext, "all notes", Toast.LENGTH_SHORT).show()
            }
            when (it.itemId) {
                R.id.nav_travel ->
                    Toast.makeText(applicationContext, "all travels", Toast.LENGTH_SHORT).show()
             }
            when (it.itemId) {
                R.id.nav_untag ->
                    Toast.makeText(applicationContext, "all untags", Toast.LENGTH_SHORT).show()
             }
            when (it.itemId) {
            R.id.nav_life ->
                Toast.makeText(applicationContext, "all lifes", Toast.LENGTH_SHORT).show()
            }
            when (it.itemId) {
                R.id.nav_birthdays ->
                    Toast.makeText(applicationContext, "all birthdays", Toast.LENGTH_SHORT).show()
            }
            when (it.itemId) {
            R.id.nav_peronal ->
                Toast.makeText(applicationContext, "all personals", Toast.LENGTH_SHORT).show()
        }
                when (it.itemId) {
                    R.id.nav_work ->
                        Toast.makeText(applicationContext, "all works", Toast.LENGTH_SHORT).show()
                }
            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getAllNotesFromDatabase()
    }

}