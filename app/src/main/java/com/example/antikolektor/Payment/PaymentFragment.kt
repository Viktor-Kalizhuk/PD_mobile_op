package com.example.antikolektor.Payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentPaymentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PaymentFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.pay.setOnClickListener {

            findNavController().navigate(R.id.action_personalAreaFragment_to_goPaymentFragment)
            dismiss()

        }
        binding.hist.setOnClickListener {

            findNavController().navigate(R.id.action_personalAreaFragment_to_historyPaymentFragment)
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