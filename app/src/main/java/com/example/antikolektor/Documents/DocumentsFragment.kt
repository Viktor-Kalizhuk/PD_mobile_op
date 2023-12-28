package com.example.antikolektor.Documents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.Main.PersonalAreaFragmentDirections
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentDocumentsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DocumentsFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentDocumentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myDoc.setOnClickListener {
            findNavController().navigate(R.id.action_personalAreaFragment_to_myDocumentsFragment)
            dismiss()
        }
        binding.shareDoc.setOnClickListener {
            findNavController().navigate(R.id.action_personalAreaFragment_to_shareDocumentFragment)
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