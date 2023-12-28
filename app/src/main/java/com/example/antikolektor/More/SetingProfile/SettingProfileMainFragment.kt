package com.example.antikolektor.More.SetingProfile


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
import com.example.antikolektor.R

import com.example.antikolektor.databinding.FragmentSetingProfileMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first


class SettingProfileMainFragment : Fragment() {
    private val exitDialog = SettingDialogFragment()
    lateinit var dataStore: DataStore<Preferences>
    lateinit var binding: FragmentSetingProfileMainBinding
    val viewModelServer: SettingProfileViewModel by lazy {
        ViewModelProvider(this)[SettingProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetingProfileMainBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")

        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getProfile("Bearer $value")
        }

        viewModelServer.profileByLiveData.observe(viewLifecycleOwner){ response ->
            if (response != null) {
                if (response.first_name != null) {
                    Toast.makeText(requireContext(), "именя есть", Toast.LENGTH_SHORT).show()
                    binding.TvName.text = response.first_name.toString()
                } else {
                    if (response.phone.first().phone != null) {
                        Toast.makeText(requireContext(), "номера есть", Toast.LENGTH_SHORT).show()
                        binding.TvName.text =response.phone.first().phone.toString()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "нет не номера не имени",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "запрос не выполнен", Toast.LENGTH_SHORT).show()}
        }

        binding.exit.setOnClickListener {
            exitDialog.show(requireActivity().supportFragmentManager,"exit")
        }
        binding.myPhone.setOnClickListener {
            findNavController().navigate(R.id.action_setingProfileMainFragment_to_myPhoneFragment)
        }
        binding.chanhePassword.setOnClickListener {
            findNavController().navigate(R.id.action_setingProfileMainFragment_to_changePassword)
        }
        binding.imageView2.setOnClickListener {
            findNavController().navigate(R.id.action_setingProfileMainFragment_to_moreFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_setingProfileMainFragment_to_moreFragment)
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