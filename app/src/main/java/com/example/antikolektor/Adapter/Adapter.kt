package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.Adapter.MultiAdapter.ElementOfListBlack
import com.example.antikolektor.DataRecycler
import com.example.antikolektor.ItemAdapter.ItemCall
import com.example.antikolektor.R
import com.example.antikolektor.RoomDatabase.User
import com.example.antikolektor.Server.Data
import com.example.antikolektor.Server.DataByResponse
import com.example.antikolektor.databinding.CustomRowBinding
import com.example.antikolektor.databinding.ItemBinding

class Adapter(private var callback1: (DataRecycler) -> Unit) : RecyclerView.Adapter<Adapter.RepozHolder>() {
    val repozList = ArrayList<Data>()

    class RepozHolder(itemView: View, private var callback1: (DataRecycler) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val binding = CustomRowBinding.bind(itemView)
        fun bind(repoz: Data,position: Int) = with(binding) {
            val ava = repoz.phone
            tvId.text = ava[0].toString()
            tvPhone.text = repoz.phone
            tvType.text = repoz.subtype
            imageButton4.setOnClickListener {
                val popup = PopupMenu(itemView.context, imageButton4)
                popup.apply {
                    menuInflater.inflate(R.menu.menu_ant, menu)

                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.delite -> {
                                val poz = position.toInt()
                                callback1.invoke(DataRecycler(repoz.id,repoz.type,repoz.phone,repoz.subtype, repoz.comment!!,poz))
//                                Toast.makeText(it.context, "delite", Toast.LENGTH_LONG).show()
                            }
                            R.id.edit -> {
                                val poz = position.toInt()
                                callback1.invoke(DataRecycler(repoz.id,repoz.type,repoz.phone,repoz.subtype,repoz.comment!!,poz))
//                                Toast.makeText(itemView.context, "edit", Toast.LENGTH_LONG).show()
                            }
                        }
                        false
                    }
                }
                popup.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return RepozHolder(view, callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position],position)
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<Data>) {
        repozList.clear()
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }

    fun removeItem(position:Int) {
        repozList.removeAt(position)
        notifyDataSetChanged()
    }

}