package com.example.antikolektor.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentGoPayBotomshitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class GoPayBottomshitFragment : BottomSheetDialogFragment() {

lateinit var binding:FragmentGoPayBotomshitBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoPayBotomshitBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ButtonPay.setOnClickListener {
            findNavController().navigate(R.id.action_personalAreaFragment_to_goPaymentFragment)
            dialog?.dismiss()
        }


    }


}