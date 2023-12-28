package com.example.antikolektor.More.SetingProfile.AddPhone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.example.antikolektor.More.SetingProfile.Serv.ServerViewModel
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentAddPhoneBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AddPhoneFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    var text = ""
    val viewModelServer: ServerViewModel by lazy {
        ViewModelProvider(this).get(ServerViewModel::class.java)
    }
    lateinit var binding: FragmentAddPhoneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.next.setOnClickListener {
            lifecycleScope.launch {
                val value = readToken("qwe")
                text = "7"+binding.edTex.text.toString()
                viewModelServer.addFutherPhone(text, "Bearer ${value}")
            }

        }

        viewModelServer.addFutherPhone.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNullOrEmpty()) {
                Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
            } else {
               findNavController().navigate(
                   AddPhoneFragmentDirections.actionAddPhoneFragment2ToGetCodeFragment(
                       result.toString(),
                       text
                   )
               )
                Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addPhoneFragment2_to_myPhoneFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_addPhoneFragment2_to_myPhoneFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}