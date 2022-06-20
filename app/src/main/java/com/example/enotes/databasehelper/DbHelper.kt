package com.example.enotes.databasehelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.enotes.models.create_note

class DbHelper(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AddNotes"
        private val TABLE_NOTES = "AddNotesTable"
        private val KEY_ID = "id"
        private val KEY_TITLE = "title"
        private val KEY_CONTENT = "content"
        private val TEXT_SIZE="text_size"
        private val TEXT_FONT="text_font"
        private val TEXT_CLR="text_clr"
        private val BG_CLR="bg_clr"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_NOTES_TABLE = ("CREATE TABLE " + TABLE_NOTES+ "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT," + KEY_CONTENT + " TEXT,"
                + TEXT_SIZE+ " TEXT," + TEXT_FONT+ " TEXT," + TEXT_CLR+ " TEXT,"
                + BG_CLR+ " TEXT "+" )")
        db?.execSQL(CREATE_NOTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES)
        onCreate(db)
    }

    fun addNotes(title : String, content : String,
                 textsize:String,textfont: String,textclr: String,
                 bgclr: String ){
     val values = ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_CONTENT, content)
        values.put(TEXT_SIZE, textsize)
        values.put(TEXT_FONT, textfont)
        values.put(TEXT_CLR,textclr )
        values.put(BG_CLR, bgclr)

        val db = this.writableDatabase

        db.insert(TABLE_NOTES, null, values)
        Log.e("success22", bgclr.toString())

        // at last we are
        // closing our database
        db.close()
    }

    //method to read data

    fun viewnote():List<create_note>{
        val selectQuery = "SELECT  * FROM $TABLE_NOTES"

        val noteList:ArrayList<create_note> = ArrayList<create_note>()

        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery, null)

//        var cursor: Cursor? = null
//        try{
//            cursor = db.rawQuery(selectQuery, null)
//        }catch (e: SQLiteException) {
//            db.execSQL(selectQuery)
//            return ArrayList()
//        }
        var note_id: Int
        var note_title: String
        var note_content: String
        if (cursor.moveToFirst()) {
            do {

                note_id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                note_title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                note_content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                val textsize : String= cursor.getString(cursor.getColumnIndexOrThrow(TEXT_SIZE))
                val textclr : String = cursor.getString(cursor.getColumnIndexOrThrow(TEXT_CLR))
                val textfont: String= cursor.getString(cursor.getColumnIndexOrThrow(TEXT_FONT))
                var bgclr: String= cursor.getString(cursor.getColumnIndexOrThrow(BG_CLR))

                val note= create_note(id = note_id, title = note_title, content = note_content,
                    txtsize = textsize, text_clr = textclr, text_font = textfont, bg_clr = bgclr)

                noteList.add(note)
            } while (cursor.moveToNext())
        }
        return noteList
    }
}