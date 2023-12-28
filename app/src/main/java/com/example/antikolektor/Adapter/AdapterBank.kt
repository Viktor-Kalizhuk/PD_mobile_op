package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.preferencesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.Bank
import com.example.antikolektor.DataHist
import com.example.antikolektor.DataRecycler
import com.example.antikolektor.R
import com.example.antikolektor.databinding.ItemCreditorBinding

class AdapterBank(private var callback1: (Bank) -> Unit): RecyclerView.Adapter<AdapterBank.RepozHolder>() {
    val repozList = mutableListOf<Bank?>()

    class RepozHolder(item: View, private var callback1: (Bank) -> Unit) :
        RecyclerView.ViewHolder(item) {
        var count =0
        val binding = ItemCreditorBinding.bind(item)
        fun bind(repoz: Bank?) = with(binding) {
            count +=1
           binding.TvOne.text = "Кредитор"+" ${(position+1)}"
            binding.TvTwo.text = repoz?.title
            binding.imageEdit.setOnClickListener {
                callback1.invoke(repoz!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_creditor, parent, false)
        return RepozHolder(view,callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<Bank?>) {
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
}