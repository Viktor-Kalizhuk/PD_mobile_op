package com.example.antikolektor.More.SetingProfile.AddPhone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.antikolektor.databinding.FragmentDeletePhoneDialogBinding


class DeletePhoneDialogFragment : DialogFragment() {

lateinit var binding:FragmentDeletePhoneDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeletePhoneDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams



        binding.dont.setOnClickListener {
            dialog!!.dismiss()
        }
        binding.exit.setOnClickListener {
            val result = "deletePhone"
            requireActivity().supportFragmentManager
                .setFragmentResult("request_key", bundleOf("extra_key" to result))
            dialog!!.dismiss()
        }
    }


}