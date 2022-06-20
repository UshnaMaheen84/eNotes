package com.example.enotes.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.R

class AdapterFile_attach(val file_uri : List<Uri> ,val file_name: ArrayList<String>, context: Context) : RecyclerView.Adapter<AdapterFile_attach.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.file_attach,parent,false);
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uri: Uri = file_uri.get(position)
        val name:String= file_name.get(position)
        holder.image.setImageURI(uri)
        holder.tv.setText(name)
        Log.e("img",uri.toString())
    }

    override fun getItemCount()= file_uri.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image: ImageView = itemView.findViewById(R.id.attach_file)
        val tv: TextView = itemView.findViewById(R.id.file_name2)
    }

}