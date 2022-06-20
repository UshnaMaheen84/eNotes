package com.example.enotes.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.R

class Adapter_img_attach(val img_uri : List<Uri>,val imgName :ArrayList<String>,context: Context) :RecyclerView.Adapter<Adapter_img_attach.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.img_attach,parent,false);
        return ViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val uri: Uri = img_uri.get(position)
        holder.image.setImageURI(uri)


    }

    override fun getItemCount()= img_uri.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image: ImageView = itemView.findViewById(R.id.attach_img)
    }


}