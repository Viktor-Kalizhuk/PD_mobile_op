package com.example.antikolektor.Payment

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
import com.example.antikolektor.Adapter.AdapterHist
import com.example.antikolektor.DataHist
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentHistoryPaymentBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HistoryPaymentFragment : Fragment() {
    var list: ArrayList<DataHist>? = null
    val adapter = AdapterHist()
    lateinit var dataStore: DataStore<Preferences>
    val viewModelServer: PaimentViewModel by lazy {
        ViewModelProvider(this)[PaimentViewModel::class.java]
    }
    lateinit var binding: FragmentHistoryPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    fun init() {
        with(binding) {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        dataStore = requireContext().createDataStore(name = "settings")
        lifecycleScope.launch {
            val value = readToken("qwe")
            viewModelServer.getHistPayment("Bearer $value")
        }

        viewModelServer.paimentHist.observe(viewLifecycleOwner){ listHist ->
            if (listHist?.data.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Unsuccesful netvork call", Toast.LENGTH_LONG)
                    .show()
                binding.consl.visibility = View.VISIBLE
            } else {
                adapter.addRepoz(listHist!!.data)
                list?.addAll(listHist.data)
                    binding.consl.visibility = View.GONE
            }
        }


        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_historyPaymentFragment_to_personalAreaFragment)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_historyPaymentFragment_to_personalAreaFragment)

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