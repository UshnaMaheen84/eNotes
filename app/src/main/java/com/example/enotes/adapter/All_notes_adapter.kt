package com.example.enotes.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.MainActivity
import com.example.enotes.R
import com.example.enotes.ViewNotes
import com.example.enotes.models.Deleting
import com.example.enotes.models.create_note

class All_notes_adapter(val arrraylist: ArrayList<create_note>,val context: Context): RecyclerView.Adapter<All_notes_adapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.itemview_show_notes,parent,false);
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notes: create_note = arrraylist.get(position)


        holder.content.setText(notes.content)
        holder.tittle.setText(notes.title)
    //    holder.tittle.setTypeface(ResourcesCompat.getFont(context, notes.text_font.toInt()))

        holder.cardView.setOnClickListener {
            val intent = Intent(context, ViewNotes::class.java)
            intent.putExtra("title",notes.title)
            intent.putExtra("content",notes.content)
            intent.putExtra("textsize",notes.txtsize)
            intent.putExtra("textclr",notes.text_clr)
            intent.putExtra("font",notes.text_font)
            intent.putExtra("bg",notes.bg_clr)
           context.startActivity (intent)

            Log.e("fontss", notes.bg_clr)

//

        }

    }

    override fun getItemCount()= arrraylist.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tags: ImageView = itemView.findViewById(R.id.tags)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        val tittle: TextView = itemView.findViewById(R.id.note_tittle)
        val content: TextView = itemView.findViewById(R.id.note_content)
        val date: TextView = itemView.findViewById(R.id.note_date)
        val cardView: CardView = itemView.findViewById(R.id.card)
    }

}