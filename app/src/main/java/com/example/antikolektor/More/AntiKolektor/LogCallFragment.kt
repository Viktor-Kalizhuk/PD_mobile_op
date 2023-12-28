package com.example.antikolektor.More.AntiKolektor

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.CallLog
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
import com.example.antikolektor.Adapter.RcCallAdapter
import com.example.antikolektor.ItemAdapter.ItemCall
import com.example.antikolektor.R

import com.example.antikolektor.databinding.FragmentLogCallBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class LogCallFragment : Fragment() {
    val viewModelServer: ViewModelBlackList by lazy {
        ViewModelProvider(this)[ViewModelBlackList::class.java]
    }

    lateinit var dataStore: DataStore<Preferences>
    private val adapterCall = RcCallAdapter { callback2(it) }
    lateinit var binding: FragmentLogCallBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogCallBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCallContact()
        initCall()
        dataStore = requireContext().createDataStore(name = "settings")
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_logCallFragment_to_antiKolektorMainFragment)
        }

        viewModelServer.posbaseUserPhones.observe(viewLifecycleOwner) { response ->
            if (response.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "ошибка запроса", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_logCallFragment_to_antiKolektorMainFragment)
            }
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_logCallFragment_to_antiKolektorMainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun callback2(item: ItemCall) {
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            val phone = item.number.toCharArray()
            when (phone[0].toString()) {
                "8" -> {
                    val number = "7" + item.number.substring(0)
                    viewModelServer.postUserPhones(
                        number,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        "Коллектор",
                        "Bearer ${value!!}"
                    )

                }
                "+" -> {
                    val number = item.number.substring(0)
                    viewModelServer.postUserPhones(
                        number,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        "Коллектор",
                        "Bearer ${value!!}"
                    )

                }
                else -> {
                    viewModelServer.postUserPhones(
                        item.number,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        "Коллектор",
                        "Bearer ${value!!}"
                    )
                }
            }
        }

    }

    private fun initCall() {
        with(binding) {
            rcCallVew.layoutManager = LinearLayoutManager(requireContext())
            rcCallVew.adapter = adapterCall
        }
    }

    @SuppressLint("Range")
    fun initCallContact() {
        var arrayCallContatct = arrayListOf<ItemCall>()
        val cursor = requireActivity().contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null, null, null, CallLog.Calls.DATE + " DESC"
        )
        cursor?.let {
            while (it.moveToNext()) {
                val number = it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
                val type = it.getString(it.getColumnIndex(CallLog.Calls.TYPE))
                val date = it.getString(it.getColumnIndex(CallLog.Calls.DATE))
                val duration = it.getString(it.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val newModel = ItemCall(0, "", "", "", "")
                newModel.number = number.replace(regex = Regex("[\\s,-]"), replacement = "").toString()
                newModel.type = type.toString()
                newModel.date = date.toString()
                if (duration != null) {
                    newModel.duration = duration
                } else {
                    newModel.duration = "name"
                }

                arrayCallContatct.add(newModel)
            }
            arrayCallContatct = arrayCallContatct.distinct() as ArrayList<ItemCall>
        }
        adapterCall.addRepoz(arrayCallContatct)
        cursor?.close()
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}