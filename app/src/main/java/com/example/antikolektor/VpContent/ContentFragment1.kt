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


class ContentFragment1 : Fragment() {
 lateinit var binding: FragmentContent1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContent1Binding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button9.setOnClickListener {
            val result = "nextTwo"
            requireActivity().supportFragmentManager
                .setFragmentResult("request", bundleOf("position" to result))
        }

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_entryStorisFragment_to_personalAreaFragment)

        }
    }
}