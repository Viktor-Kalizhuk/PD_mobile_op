package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.antikolektor.*
import com.example.antikolektor.databinding.ItemPhotoBinding

import java.util.*
import kotlin.collections.ArrayList

class AdapterPhoto(private var callback1: (DocumentsStrPoz) -> Unit) :
    RecyclerView.Adapter<AdapterPhoto.RepozHolder>() {
    val repozList = ArrayList<DocumentsStrr>()

    class RepozHolder(item: View, private var callback1: (DocumentsStrPoz) -> Unit) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemPhotoBinding.bind(item)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(repoz: DocumentsStrr, position: Int) = with(binding) {
            when (repoz.bitmap) {
                null -> {
                    if (repoz.sender_id == repoz.user_id) {
                        Glide
                            .with(itemView)
                            .load("https://lk.pravoe-delo.su/storage/" + repoz.path)
                            .into(binding.imageView6)
                        imageButton2.setImageResource(R.drawable.carbon_trash_can)
                    } else {
                        binding.imageView6.setImageResource(R.drawable.leading_element)
                        imageButton2.setImageResource(R.drawable.carbon_download)
                    }
                    textView9.text = repoz.created_at
                    TvTitle.text = repoz.name

                }
                else->{
                    imageView6.setImageBitmap(repoz.bitmap)
                }
            }
            imageButton2.setOnClickListener {
                when(repoz.bitmap){
                    null->{
                        callback1.invoke(
                            DocumentsStrPoz(
                                repoz.id,
                                position,
                                action = false,
                                repoz.path,
                                repoz.sender_id,
                                repoz.user_id,
                                null
                            )
                        )
                    }
                    else->{
                        callback1.invoke(
                            DocumentsStrPoz(
                                repoz.id,
                                position,
                                action = false,
                                repoz.path,
                                repoz.sender_id,
                                repoz.user_id,
                                repoz.bitmap
                            )
                        )
                    }

                }


            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return RepozHolder(view, callback1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position], position)
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<DocumentsStrr>) {
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        repozList.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItemAll() {
        repozList.clear()
        notifyDataSetChanged()
    }
}
