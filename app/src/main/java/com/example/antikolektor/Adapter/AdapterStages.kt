package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.DataHist
import com.example.antikolektor.DataListStages
import com.example.antikolektor.DataStages
import com.example.antikolektor.R
import com.example.antikolektor.databinding.HistoryItemBinding
import com.example.antikolektor.databinding.ItemStagesBinding

class AdapterStages(private var callbac1:(DataStages) ->Unit) : RecyclerView.Adapter<AdapterStages.RepozHolder>() {
    val repozList = mutableListOf<DataStages>()

    class RepozHolder(item: View,private var callbac1:(DataStages) ->Unit) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemStagesBinding.bind(item)
        fun bind(repoz: DataStages) = with(binding) {
            TvDate.text = repoz.active_date
            TvTitle.text = repoz.title
            CardView.setOnClickListener {
                callbac1.invoke(repoz)
            }
            when (repoz.active) {
                "F" -> {
                    TvStatus.text = "Запланировано"
                    LinStatus.setBackgroundResource(R.drawable.bac_yelow)
                }
                "N" -> {
                    TvStatus.text = "Выполнено"
                    LinStatus.setBackgroundResource(R.drawable.bac_gereen)
                }
                "Y" -> {
                    TvStatus.text = "В работе"
                    LinStatus.setBackgroundResource(R.drawable.bac_blue)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_stages, parent, false)
        return RepozHolder(view,callbac1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<DataStages>) {
        repozList.clear()
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
}