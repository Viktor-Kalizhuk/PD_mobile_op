package com.example.antikolektor.More.AboutTheApplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentAboutTheApplicationBinding


class AboutTheApplicationFragment : Fragment() {

lateinit var binding: FragmentAboutTheApplicationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutTheApplicationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ButtonPolit.setOnClickListener {
            val browserIntent: Intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://pravoe-delo.su/personalnie-dannie"))
            startActivity(browserIntent)
        }
        binding.ButtonDogovor.setOnClickListener {
            val browserIntent: Intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://lk.pravoe-delo.su/oferta"))
            startActivity(browserIntent)
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_aboutTheApplicationFragment_to_moreFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_aboutTheApplicationFragment_to_moreFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}