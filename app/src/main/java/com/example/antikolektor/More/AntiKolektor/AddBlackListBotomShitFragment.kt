package com.example.antikolektor.More.AntiKolektor

import android.app.AlertDialog
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R

import com.example.antikolektor.databinding.FragmentAddBlackListBotomShitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddBlackListBotomShitFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddBlackListBotomShitBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBlackListBotomShitBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addContact.setOnClickListener {
            findNavController().navigate(R.id.action_antiKolektorMainFragment_to_contactFragment)
            dismiss()
        }
        binding.addLogCall.setOnClickListener {
            findNavController().navigate(R.id.action_antiKolektorMainFragment_to_logCallFragment)
            dismiss()
        }
        binding.writeNum.setOnClickListener {
            val result = "dialog"
            requireActivity().supportFragmentManager
                .setFragmentResult("request_key", bundleOf("extra_key" to result))

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


