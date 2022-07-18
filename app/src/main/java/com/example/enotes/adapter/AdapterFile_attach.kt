package com.example.enotes.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.enotes.OpenImage
import com.example.enotes.R
import com.example.enotes.models.ImageModel

class AdapterFile_attach(
 val arrayList: ArrayList<ImageModel>,
    val context: Context)
    : RecyclerView.Adapter<AdapterFile_attach.ViewHolder>(){
//    private var listData: MutableList<Uri> = data as MutableList<Uri>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.file_attach,parent,false);
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ImageModel = arrayList.get(position)
//
//        model.uri= file_uri.get(position)
//        model.name= file_name.get(position)
//        val uri: Uri = file_uri.get(position)
//        val name:String= file_name.get(position)
        holder.image.setImageURI(model.uri)
        holder.image.setOnClickListener {
            val intent = Intent(context, OpenImage::class.java)
            intent.putExtra("ImageUri", model.uri.toString())

            context.startActivity(intent)
        }

        holder.image.setOnLongClickListener{

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Item deletion")
            dialog.setMessage("Do you want to delete this item?")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                deleteItem(position)

            })

            dialog.show()

            true
        }
        Log.e("img", model.uri.toString())
    }

    override fun getItemCount()= arrayList.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val card= itemView.findViewById<CardView>(R.id.img_card)
        val image: ImageView = itemView.findViewById(R.id.attach_file)
    }
    fun deleteItem(position: Int) {


            arrayList.removeAt(position)
            notifyItemRemoved(position)


        Log.e("delit", "del")

    }

}