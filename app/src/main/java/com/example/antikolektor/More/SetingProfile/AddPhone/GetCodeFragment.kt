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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.antikolektor.More.SetingProfile.Serv.SettingViewModel
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentGetCodeBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class GetCodeFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    val args: GetCodeFragmentArgs by navArgs()
    val viewModelServer: SettingViewModel by lazy {
        ViewModelProvider(this).get(SettingViewModel::class.java)
    }
    lateinit var binding: FragmentGetCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.button11.setOnClickListener {
            lifecycleScope.launch {
                val value = readToken("qwe")
                if (binding.pinView.text.toString() == args.code) {
                    viewModelServer.confirmFurtherPhone(
                        args.phone,
                        binding.pinView.text.toString(),
                        "Bearer ${value}"
                    )
                    Toast.makeText(requireContext(), "успех", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_getCodeFragment_to_myPhoneFragment)
                }
                else{
                    Toast.makeText(requireContext(), "Неверно введен код", Toast.LENGTH_SHORT).show()
                }
            }


        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_getCodeFragment_to_addPhoneFragment2)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_getCodeFragment_to_addPhoneFragment2)
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