package com.example.antikolektor.Documents.MyDocumentsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import com.example.antikolektor.databinding.FragmentAddPhotoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddPhotoFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addGalery.setOnClickListener {
            val result = "addG"
            requireActivity().supportFragmentManager
                .setFragmentResult("request_key", bundleOf("key" to result))
            dismiss()
        }
        binding.makePhoto.setOnClickListener {
            val result = "makeP"
            requireActivity().supportFragmentManager
                .setFragmentResult("request_key", bundleOf("key" to result))
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
