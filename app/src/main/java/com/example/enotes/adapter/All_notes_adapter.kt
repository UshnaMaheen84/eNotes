package com.example.enotes.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.R
import com.example.enotes.activities.EditNoteActivity
import com.example.enotes.models.create_note
import kotlin.collections.ArrayList

class All_notes_adapter
    (val arrraylist: ArrayList<create_note>, val context: Context) :

    RecyclerView.Adapter<All_notes_adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemview_show_notes, parent, false);
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notes: create_note = arrraylist.get(position)


        holder.content.setText(notes.content)
        holder.tittle.setText(notes.title)
        holder.tittle.setTextColor(Integer.parseInt(notes.text_clr))
        holder.content.setTextColor(Integer.parseInt(notes.text_clr))
        //    holder.tittle.setTypeface(ResourcesCompat.getFont(context, notes.text_font.toInt()))
         val bookmark: String= notes.bookmark
        if (bookmark=="red")
        {
            holder.tags.setImageResource(R.drawable.bookmark_red)
        }
        if (bookmark=="blue")
        {
            holder.tags.setImageResource(R.drawable.bookmark_blue)
        }
        if (bookmark=="green")
        {
            holder.tags.setImageResource(R.drawable.bookmark_grren)
        }
        if (bookmark=="yellow")
        {
            holder.tags.setImageResource(R.drawable.bookmark_yellow)
        }
        if (bookmark=="orange")
        {
            holder.tags.setImageResource(R.drawable.bookmark_orage)
        }
        if (bookmark=="untag")
        {
            holder.tags.setImageResource(R.drawable.bookmark_border_24)
        }

        holder.date.text= notes.currentDate
        holder.cardView.setOnClickListener {

            val intent = Intent(context, EditNoteActivity::class.java)
            intent.putExtra("title", notes.title)
            intent.putExtra("content", notes.content)
            intent.putExtra("textsize", notes.txtsize)
            intent.putExtra("textclr", notes.text_clr)
            intent.putExtra("font", notes.text_font)
            intent.putExtra("bg", notes.bg_clr)
            intent.putExtra("reminder_date",notes.reminderDate)
            intent.putExtra("address", notes.address)
            context.startActivity(intent)

            Log.e("fontss", notes.bg_clr)


        }

        holder.delete.setOnClickListener {

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Item deletion")
            dialog.setMessage("Do you want to delete this item?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                deleteItem(position)
            })

            dialog.show()


        }
    }




    override fun getItemCount() = arrraylist.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tags: ImageView = itemView.findViewById(R.id.tags)
        val delete: ImageView = itemView.findViewById(R.id.delete)
        val tittle: TextView = itemView.findViewById(R.id.note_tittle)
        val content: TextView = itemView.findViewById(R.id.note_content)
        val date: TextView = itemView.findViewById(R.id.note_date)
        val cardView: CardView = itemView.findViewById(R.id.card)
    }

    fun deleteItem(position: Int) {
        arrraylist.removeAt(position)
        notifyItemRemoved(position)
        Log.e("delit", "del")

    }
}