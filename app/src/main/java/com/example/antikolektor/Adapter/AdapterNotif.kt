package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.renderscript.Element.DataKind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.*
import com.example.antikolektor.ItemAdapter.Item
import com.example.antikolektor.databinding.HistoryItemBinding
import com.example.antikolektor.databinding.NotificationItemBinding

class AdapterNotif(private var callback1: (DataNotifCallback) -> Unit) : RecyclerView.Adapter<AdapterNotif.RepozHolder>() {
    val repozList = mutableListOf<DataNotif>()

    class RepozHolder(item: View, private var callback1: (DataNotifCallback) -> Unit) :
        RecyclerView.ViewHolder(item) {
        val binding = NotificationItemBinding.bind(item)
        fun bind(repoz: DataNotif,position: Int) = with(binding) {
           message.text = repoz.message
            date.text = repoz.date
            binding.ConsNotif.setOnClickListener {
                callback1.invoke(DataNotifCallback(repoz.id,repoz.type,repoz.message,repoz.date,position))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return RepozHolder(view, callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position],position)
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<DataNotif>) {
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
    fun removeItem(position:Int) {
        repozList.removeAt(position)
        notifyDataSetChanged()
    }
}