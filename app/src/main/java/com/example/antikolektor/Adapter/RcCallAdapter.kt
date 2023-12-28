package com.example.antikolektor.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.ItemAdapter.ItemCall
import com.example.antikolektor.R
import com.example.antikolektor.databinding.ItemCallBinding
import java.text.SimpleDateFormat
import java.util.*


class RcCallAdapter(private var callback2: (ItemCall) -> Unit) :
    RecyclerView.Adapter<RcCallAdapter.RepozHolder>() {
    val repozList = ArrayList<ItemCall>()

    class RepozHolder(item: View, private var callback2: (ItemCall) -> Unit) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemCallBinding.bind(item)

        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(repoz: ItemCall) = with(binding) {
            binding.layout.setOnClickListener {
                callback2.invoke(repoz)
            }

            if(repoz.duration=="name"){
                binding.textView2.visibility = View.GONE
                val ava = repoz.number.toCharArray()
                binding.textView43.text = ava[1].toString()
            }else{
                binding.textView2.text = repoz.duration
                binding.textView2.visibility = View.VISIBLE
                val ava = repoz.duration.toCharArray()
                binding.textView43.text = ava[0].toString()
            }

            binding.textView3.text = repoz.number

            val date =
                SimpleDateFormat("dd MMM , hh:mm ").format(Date(repoz.date.toLong()))

            binding.textView12.text = date.toString()
            when (repoz.type) {
                "2" -> {
                    binding.image.setImageResource(R.drawable.material_symbols_call_made_rounded)
                }
                "1" -> {
                    binding.image.setImageResource(R.drawable.material_symbols_call_received_round)
                }
                "3" -> {
                    binding.image.setImageResource(R.drawable.material_symbols_call_received_rounded)
                }
                "6" -> {
                    binding.image.setImageResource(R.drawable.carbon_rule_cancelled)
                }
                "5" -> {
                    binding.image.setImageResource(R.drawable.carbon_error)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_call, parent, false)
        return RepozHolder(view, callback2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<ItemCall>) {
        repozList.clear()
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
}
