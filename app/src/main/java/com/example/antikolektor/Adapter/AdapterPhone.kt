package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.*
import com.example.antikolektor.Server.Data
import com.example.antikolektor.databinding.CustomRowBinding
import com.example.antikolektor.databinding.ItemCreditorBinding
import com.example.antikolektor.databinding.ItemMyPhoneBinding

class AdapterPhone(private var callback1: (PhoneProfilePoz) -> Unit) :
    RecyclerView.Adapter<AdapterPhone.RepozHolder>() {
    val repozList = mutableListOf<PhoneProfile?>()

    class RepozHolder(item: View, private var callback1: (PhoneProfilePoz) -> Unit) :
        RecyclerView.ViewHolder(item) {

        val binding = ItemMyPhoneBinding.bind(item)
        fun bind(repoz: PhoneProfile?,position: Int) = with(binding) {
            TvPhone.text = repoz?.phone.toString()
            ImViewTrash.setOnClickListener {
                callback1.invoke(PhoneProfilePoz(repoz?.phone,position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_phone, parent, false)
        return RepozHolder(view, callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position],position)
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: PhoneProfile?) {
        repozList.add(repoz)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        repozList.removeAt(position)
        notifyDataSetChanged()
    }
}