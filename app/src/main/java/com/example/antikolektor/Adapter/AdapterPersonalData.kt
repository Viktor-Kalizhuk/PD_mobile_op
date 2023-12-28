package com.example.antikolektor.Adapter

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.antikolektor.*
import com.example.antikolektor.databinding.ItemPersonalDataBinding


class AdapterPersonalData(private var callback1: (DocumentsStrPoz) -> Unit) :
    RecyclerView.Adapter<AdapterPersonalData.RepozHolder>() {
    val repozList = arrayListOf<MyDocumentDataStruct>()

    class RepozHolder(itemView: View, private var callback1: (DocumentsStrPoz) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ItemPersonalDataBinding.bind(itemView)
        lateinit var adapter: AdapterPhoto

        fun bind(repoz: MyDocumentDataStruct, position: Int) = with(binding) {
            button5.setOnClickListener {
                callback1.invoke(
                    DocumentsStrPoz(
                        repoz.structure.first().id,
                        position,
                        action = true,
                        " ",
                        0, 0, null

                    )
                )
            }


            TvTitle.text = repoz.structure.first().title
            adapter = AdapterPhoto { callback2(it) }
            rcView.adapter = adapter
            val arraylist = arrayListOf<DocumentsStrr>()
            repoz.documents.forEach {
                arraylist.add(
                    DocumentsStrr(
                        it.id,
                        it.name,
                        it.original_name,
                        it.type,
                        it.size,
                        it.path,
                        it.sort,
                        it.user_id,
                        it.file_type_id,
                        it.stage_id,
                        it.credit_id,
                        it.sender_id,
                        it.created_at,
                        it.updated_at,
                        null
                    )
                )
            }
            adapter.addRepoz(arraylist)
        }

        private fun callback2(repoz: DocumentsStrPoz) {
            callback1.invoke(
                DocumentsStrPoz(
                    repoz.id,
                    repoz.pos,
                    action = false,
                    repoz.path,
                    repoz.sender_id,
                    repoz.user_id, null

                )
            )
            adapter.removeItem(repoz.pos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_personal_data, parent, false)
        return RepozHolder(view, callback1)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position], position)
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRepoz(repoz: List<MyDocumentDataStruct>) {
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllItem() {

        repozList.clear()

        notifyDataSetChanged()
    }


}