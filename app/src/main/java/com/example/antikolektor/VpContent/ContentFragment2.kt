package com.example.antikolektor.VpContent

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentContent1Binding
import com.example.antikolektor.databinding.FragmentContent2Binding


class ContentFragment2 : Fragment() {
 lateinit var binding: FragmentContent2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContent2Binding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button9.setOnClickListener {
            val result = "next3"
            requireActivity().supportFragmentManager
                .setFragmentResult("request", bundleOf("position" to result))
        }

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_entryStorisFragment_to_personalAreaFragment)

        }
    }
}