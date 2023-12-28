package com.example.antikolektor.More

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.antikol.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_antiKolektorMainFragment)
        }
        binding.ContactWithSpecialist.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_contactWithSpecialistFragment)
        }
        binding.SetingProfile.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_setingProfileMainFragment)
        }
        binding.infPril.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_aboutTheApplicationFragment)
        }
        binding.infComp.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_aboutCompany)
        }
        binding.leaveFeedback.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_leaveFeedbackFragment)
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_personalAreaFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_moreFragment_to_personalAreaFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}