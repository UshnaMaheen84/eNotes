package com.example.enotes.models

import android.net.Uri

class create_note {


    val id: Int
    val bg_clr: String
    val txtsize: String
    val text_font: String
    val text_clr: String
    val title: String
    val content: String
    val address: String
    val bookmark: String
    val currentDate: String

    var imageUrl = ArrayList<String>()

    //val fileUrl: ArrayList<String>
    constructor(
        id: Int,
        bg_clr: String,
        txtsize: String,
        text_font: String,
        text_clr: String,
        title: String,
        content: String,
        address: String,
        bookmark: String,
        currentdate:String
    )
    {
        this.id = id
        this.bg_clr = bg_clr
        this.txtsize = txtsize
        this.text_font = text_font
        this.text_clr = text_clr
        this.title = title
        this.content = content
        this.address = address
        this.bookmark= bookmark
        this.currentDate= currentdate
    }



}