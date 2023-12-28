package com.example.antikolektor.Documents.ShareDocuments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentShareDocumentBinding
import com.example.antikolektor.databinding.FragmentSharedAddDocumentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SharedAddDocumentFragment : BottomSheetDialogFragment() {

    lateinit var bindind: FragmentSharedAddDocumentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentSharedAddDocumentBinding.inflate(layoutInflater)
        return bindind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindind.addGalery.setOnClickListener {
            val result = "addGG"
            requireActivity().supportFragmentManager
                .setFragmentResult("request", bundleOf("key" to result))
            dismiss()
        }
        bindind.makePhoto.setOnClickListener {
            val result = "makePP"
            requireActivity().supportFragmentManager
                .setFragmentResult("request", bundleOf("key" to result))
            dismiss()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dismiss()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}