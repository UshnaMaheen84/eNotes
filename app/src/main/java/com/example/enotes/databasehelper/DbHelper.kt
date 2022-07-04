package com.example.enotes.databasehelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.enotes.models.create_note
import kotlin.math.sqrt

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    lateinit var myDbInstance: SQLiteDatabase

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AddNotes"
        private val TABLE_NOTES = "AddNotesTable"
        private val KEY_ID = "id"
        private val KEY_TITLE = "title"
        private val KEY_CONTENT = "content"
        private val TEXT_SIZE = "text_size"
        private val TEXT_FONT = "text_font"
        private val TEXT_CLR = "text_clr"
        private val BG_CLR = "bg_clr"
        private val ADDRESS = "address"

        // table for saving notes sketch images
        private val TABLE_NOTES_SKETCH_IMAGE = "Notes_Sketch"
        private val KEY_IMAGE_ID = "image_id"
        private val KEY_NOTE_ID = "note_id"
        private val KEY_IMAGE_URI = "image_uri"

        // table for saving notes file images


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_NOTES_TABLE = ("CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT," + KEY_CONTENT + " TEXT,"
                + TEXT_SIZE + " TEXT," + TEXT_FONT + " TEXT," + ADDRESS + " TEXT," + TEXT_CLR + " TEXT,"
                + BG_CLR + " TEXT " + " )")
        val CREATE_NOTES_IMAGE_TABLE = ("CREATE TABLE " + TABLE_NOTES_SKETCH_IMAGE + "( "
                + KEY_IMAGE_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOTE_ID + " INTEGER,"
                + KEY_IMAGE_URI + " TEXT " + ")"
                )
        db?.execSQL(CREATE_NOTES_TABLE)
        db?.execSQL(CREATE_NOTES_IMAGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES_SKETCH_IMAGE)
        onCreate(db)
    }

    fun addNotes(
        title: String, content: String,
        textsize: String, textfont: String, textclr: String,
        bgclr: String, address: String, sketchList: ArrayList<String>
    ) {
        //open Database here
        openDB()
        val values = ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_CONTENT, content)
        values.put(TEXT_SIZE, textsize)
        values.put(TEXT_FONT, textfont)
        values.put(TEXT_CLR, textclr)
        values.put(BG_CLR, bgclr)
        values.put(ADDRESS, address)

        var index = myDbInstance.insert(TABLE_NOTES, null, values)
        Log.e("success22", "index of notes -> $index")

        addSketchImageOfNotes(index.toInt(), sketchList)

        //close Database here when all operation done
        closeDB()
    }

    fun openDB() {
        myDbInstance = this.writableDatabase
    }

    fun closeDB() {
        myDbInstance.close()
    }

    fun addSketchImageOfNotes(noteID: Int, sketchList: ArrayList<String>) {
        openDB()
        for (i in 0..sketchList.size.minus(1)) {
            val values = ContentValues()
            values.put(KEY_IMAGE_URI, sketchList.get(i))
            values.put(KEY_NOTE_ID, noteID)
            myDbInstance.insert(TABLE_NOTES_SKETCH_IMAGE, null, values)
        }

        closeDB()
    }

    //method to read data
    fun viewnote(): List<create_note> {
        openDB()


        val selectQuery = "SELECT  * FROM $TABLE_NOTES"
        val noteList: ArrayList<create_note> = ArrayList<create_note>()
        val cursor = myDbInstance.rawQuery(selectQuery, null)

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
                val textsize: String = cursor.getString(cursor.getColumnIndexOrThrow(TEXT_SIZE))
                val textclr: String = cursor.getString(cursor.getColumnIndexOrThrow(TEXT_CLR))
                val textfont: String = cursor.getString(cursor.getColumnIndexOrThrow(TEXT_FONT))
                var bgclr: String = cursor.getString(cursor.getColumnIndexOrThrow(BG_CLR))
                var addres: String = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS))

                val note = create_note(
                    id = note_id,
                    title = note_title,
                    content = note_content,
                    txtsize = textsize,
                    text_clr = textclr,
                    text_font = textfont,
                    bg_clr = bgclr,
                    address = addres
                )

                /**
                 * Code to get the sketch image of any note
                 */
                val selectedQueryForSketchImagesOfNotes =
                    "SELECT * FROM $TABLE_NOTES_SKETCH_IMAGE where $KEY_NOTE_ID = '" + note_id + "'"
                var sketchList = ArrayList<String>()
                val innnerCursor = myDbInstance.rawQuery(selectedQueryForSketchImagesOfNotes, null)
                if (innnerCursor.moveToFirst()) {
                    do {
                        sketchList.add(
                            innnerCursor.getString(
                                innnerCursor.getColumnIndexOrThrow(
                                    KEY_IMAGE_URI
                                )
                            )
                        )
                    } while (innnerCursor.moveToNext())
                }

                note.imageUrl = sketchList

                noteList.add(note)
            } while (cursor.moveToNext())
        }

        closeDB()
        return noteList
    }
}