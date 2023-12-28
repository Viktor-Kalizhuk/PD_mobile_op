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
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.antikolektor.Adapter.AdapterPhone


import com.example.antikolektor.PhoneProfilePoz
import com.example.antikolektor.R

import com.example.antikolektor.databinding.FragmentMyPhoneBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MyPhoneFragment : Fragment() {
    private var selectedSort: String? = null
    lateinit var dataStore: DataStore<Preferences>
    val recyclerAdapter = AdapterPhone { callback1(it) }
    var position: Int? = null
    var phone: String? = null
    val viewModelServer: MyPhoneViewModel by lazy {
        ViewModelProvider(this)[MyPhoneViewModel::class.java]
    }
    private val dialogDeletePhone = DeletePhoneDialogFragment()
    lateinit var binding: FragmentMyPhoneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    fun callback1(it: PhoneProfilePoz) {
        position = it.position
        phone = it.phone
        dialogDeletePhone.show(requireActivity().supportFragmentManager, "deletePhone")
    }

    fun init() {
        with(binding) {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = recyclerAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        init()
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "request_key", requireActivity()
        ) { _, bundle ->
            selectedSort = bundle.getString("extra_key")
            when (selectedSort) {
                "deletePhone" -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val value = readToken("qwe")
                        viewModelServer.deleteFurtherPhone(phone!!, "Bearer $value")
                    }
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getProfile("Bearer $value")
        }
        viewModelServer.deletePhoneByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == "OK") {
                recyclerAdapter.removeItem(position!!)
                binding.Error.visibility = View.GONE
            } else {
                binding.Error.visibility = View.VISIBLE
            }
        }
        viewModelServer.profileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == null) {
                Toast.makeText(requireContext(), "ошибка запроса", Toast.LENGTH_SHORT).show()
            } else {
                response.phone.forEach {
                    recyclerAdapter.addRepoz(it)
                    binding.Error.visibility = View.GONE
                }
            }
        }
        binding.button10.setOnClickListener {
            findNavController().navigate(R.id.action_myPhoneFragment_to_addPhoneFragment2)
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_myPhoneFragment_to_setingProfileMainFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_myPhoneFragment_to_setingProfileMainFragment)
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