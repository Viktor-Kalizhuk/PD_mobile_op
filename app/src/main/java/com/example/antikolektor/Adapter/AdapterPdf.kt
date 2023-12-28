package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.R
import com.example.antikolektor.databinding.ItemPhotoBinding
import com.example.antikolektor.databinding.PdfItemBinding

class AdapterPdf() : RecyclerView.Adapter<AdapterPdf.RepozHolder>() {
    val repozList = ArrayList<PdfName>()

    class RepozHolder(item: View) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemPhotoBinding.bind(item)
        fun bind(repoz: PdfName) = with(binding) {
            imageView6.setImageResource(R.drawable.leading_element)
            textView9.text = repoz.pdfName.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return RepozHolder(view)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: PdfName) {
        repozList.add(repoz)
        notifyDataSetChanged()
    }
}
