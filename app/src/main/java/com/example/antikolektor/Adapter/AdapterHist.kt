package com.example.antikolektor.Adapter


import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.DataHist

import com.example.antikolektor.R
import com.example.antikolektor.databinding.HistoryItemBinding


class AdapterHist : RecyclerView.Adapter<AdapterHist.RepozHolder>() {
    val repozList = mutableListOf<DataHist>()

    class RepozHolder(item: View) :
        RecyclerView.ViewHolder(item) {
        val binding = HistoryItemBinding.bind(item)
        fun bind(repoz: DataHist) = with(binding) {
            date.text = repoz.payed_at.toString()
            if (repoz.paid == "Y") {
                position.text = "Оплачено"
                position.setBackgroundResource(R.drawable.bac_gereen)
            }
            salary.text = repoz.amount + " руб"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return RepozHolder(view)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<DataHist>) {
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
}
