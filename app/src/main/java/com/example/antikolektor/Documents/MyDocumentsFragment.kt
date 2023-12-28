package com.example.antikolektor.Documents


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.AdapterDocuments

import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentMyDocumentsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MyDocumentsFragment : Fragment() {

    lateinit var dataStore: DataStore<Preferences>
    var list = arrayListOf<String>()
    val viewModelServer: MyDocumentViewModel by lazy {
        ViewModelProvider(this)[MyDocumentViewModel::class.java]
    }
    val adapter = AdapterDocuments { callback1(it) }
    lateinit var binding: FragmentMyDocumentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyDocumentsBinding.inflate(layoutInflater)
        return binding.root
    }

    fun init() {
        with(binding) {
            RcView.layoutManager = LinearLayoutManager(requireContext())
            RcView.adapter = adapter
        }
    }

    fun callback1(it: String) {
        findNavController().navigate(MyDocumentsFragmentDirections.actionMyDocumentsFragmentToPersonalDataFragment(it))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
//
        dataStore = requireContext().createDataStore(name = "settings")


        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getClientFile("Bearer $value")
        }

        viewModelServer.documentByLiveData.observe(viewLifecycleOwner) { response ->
            list.clear()
            if (response?.structure != null) {
                response.structure.forEach {
                    list.add(it.category)
                }
                adapter.addRepoz(list.distinct())

            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_myDocumentsFragment_to_personalAreaFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_myDocumentsFragment_to_personalAreaFragment)
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