package com.example.antikolektor.Adapter.MultiAdapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.DataRecycler
import com.example.antikolektor.ItemAdapter.ItemCall
import com.example.antikolektor.R
import com.example.antikolektor.databinding.ItemCallBinding
import com.example.antikolektor.databinding.ItemCallSortBinding
import com.example.antikolektor.databinding.ItemUserSectionBinding
import java.text.SimpleDateFormat


const val VIEW_TYPE_SECTION = 1
const val VIEW_TYPE_ITEM = 2

class MultiAdapterCall(private var callback2: (ElementOfList.Item) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = mutableListOf<ElementOfList>()

    override fun getItemViewType(position: Int): Int {
        if (data[position] is ElementOfList.DataDate) {
            return VIEW_TYPE_SECTION
        }
        return VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_SECTION) {
            return SectionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_section, parent, false)
            )
        }
        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_call_sort, parent, false), callback2
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when (holder) {
            is SectionViewHolder -> holder.bind(item as ElementOfList.DataDate)
            is ContentViewHolder -> holder.bind(item as ElementOfList.Item)
        }

    }

    internal inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserSectionBinding.bind(itemView)
        fun bind(item: ElementOfList.DataDate) = with(binding) {
            textSection.text = item.date

        }
    }

    internal inner class ContentViewHolder(itemView: View, private var callback2: (ElementOfList.Item) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCallSortBinding.bind(itemView)

        @SuppressLint("SimpleDateFormat")
        fun bind(item: ElementOfList.Item) = with(binding) {

            if (item.duration != "name") {
                textView2.visibility = View.VISIBLE
                val ava = item.duration
                textView43.text = ava[0].toString()
                textView2.text = item.duration
            } else {
                val ava = item.number
                textView43.text = ava[1].toString()
                textView2.visibility = View.GONE
            }

            ImMenu.setOnClickListener {
                val popup = PopupMenu(itemView.context, ImMenu)
                popup.apply {
                    menuInflater.inflate(R.menu.menu_call, menu)

                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.callItem -> {
callback2.invoke(item)
                            }

                        }
                        false
                    }
                }
                popup.show()
            }

            binding.textView3.text = item.number
            val date =
                SimpleDateFormat("dd MMM , hh:mm ").format(java.util.Date(item.date.toLong()))

            binding.textView12.text = date.toString()
            when (item.type) {
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

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(item: List<ElementOfList>) {
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }
}


