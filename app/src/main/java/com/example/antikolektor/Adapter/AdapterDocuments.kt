package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.DataDoc
import com.example.antikolektor.DataRecycler
import com.example.antikolektor.R
import com.example.antikolektor.Server.Data
import com.example.antikolektor.databinding.CustomRowBinding
import com.example.antikolektor.databinding.ItemMainDocumentBinding

class AdapterDocuments(private var callback1: (String) -> Unit) :
    RecyclerView.Adapter<AdapterDocuments.RepozHolder>() {
    val repozList = ArrayList<String>()

    class RepozHolder(itemView: View, private var callback1: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ItemMainDocumentBinding.bind(itemView)
        fun bind(repoz: String, position: Int) = with(binding) {
            binding.TvTitle.text = repoz.toString()
            binding.TvTitle.setOnClickListener {
                callback1.invoke(repoz)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main_document, parent, false)
        return RepozHolder(view, callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position], position)
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<String>) {
        repozList.clear()
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        repozList.removeAt(position)
        notifyDataSetChanged()
    }

}