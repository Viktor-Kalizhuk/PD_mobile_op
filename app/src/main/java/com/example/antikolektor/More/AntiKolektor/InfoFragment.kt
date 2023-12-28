package com.example.antikolektor.More.AntiKolektor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class InfoFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
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