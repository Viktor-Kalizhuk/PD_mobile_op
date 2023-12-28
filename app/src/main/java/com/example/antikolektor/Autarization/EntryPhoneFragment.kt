package com.example.antikolektor.Autarization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.Server.ViewModel.SharedViewModel
import com.example.antikolektor.databinding.FragmentEntryPhoneBinding

class EntryPhoneFragment : Fragment() {
    lateinit var binding: FragmentEntryPhoneBinding
    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }
    lateinit var phone: String
    lateinit var code: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryPhoneBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {

            phone =("7"+binding.edText.text.toString())
            viewModel.refreshCode(phone)
        }
        viewModel.codeByIdLiveData.observe(requireActivity()) { response ->
            if (response == null) {
                Toast.makeText(requireContext(), "Unsuccesful necvork call", Toast.LENGTH_LONG)
                    .show()
                return@observe
            }
            code = response.code

            findNavController().navigate(
                EntryPhoneFragmentDirections.actionEntryPhoneFragmentToEntryPaswordFragment(
                    code,
                    phone
                )
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}