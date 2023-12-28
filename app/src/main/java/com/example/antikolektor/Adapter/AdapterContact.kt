package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.ItemAdapter.Item
import com.example.antikolektor.R
import com.example.antikolektor.databinding.ItemBinding

class AdapterContact(private var callback1: (Item) -> Unit) :
    RecyclerView.Adapter<AdapterContact.RepozHolder>() {
    val repozList = ArrayList<Item>()

    class RepozHolder(item: View, private var callback1: (Item) -> Unit) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemBinding.bind(item)
        fun bind(repoz: Item) = with(binding) {
            val ava = repoz.name.toCharArray()
            binding.textView43.text = ava[0].toString()
            binding.textView2.text = repoz.name
            binding.textView3.text = repoz.phone
            binding.layout.setOnClickListener {
                callback1.invoke(repoz)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return RepozHolder(view, callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<Item>) {
        repozList.clear()
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
}
