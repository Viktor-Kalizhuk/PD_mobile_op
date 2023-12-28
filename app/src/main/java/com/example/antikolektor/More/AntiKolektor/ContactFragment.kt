package com.example.antikolektor.More.AntiKolektor

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
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
import com.example.antikolektor.Adapter.AdapterContact
import com.example.antikolektor.ItemAdapter.Item
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentContactBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ContactFragment : Fragment() {

    val viewModelServer: ViewModelBlackList by lazy {
        ViewModelProvider(this)[ViewModelBlackList::class.java]
    }
    private val adapter = AdapterContact { callback1(it) }
    lateinit var binding: FragmentContactBinding
    lateinit var dataStore: DataStore<Preferences>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        initContact()
        init()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_contactFragment_to_antiKolektorMainFragment)
        }
        viewModelServer.posbaseUserPhones.observe(viewLifecycleOwner) { response ->
            if (response.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "ошибка запроса", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_contactFragment_to_antiKolektorMainFragment)
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_contactFragment_to_antiKolektorMainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    fun callback1(item: Item) {
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            val phone = item.phone.toCharArray()
            var number = " "
            when (phone[0].toString()) {
                "8" -> {

                    number = "7${item.phone.substring(1)}"

                    viewModelServer.postUserPhones(
                        number,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        "Коллектор",
                        "Bearer $value"
                    )

                }
                "+" -> {
                    number = item.phone.substring(0)
                    viewModelServer.postUserPhones(
                        number,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        "Коллектор",
                        "Bearer value"
                    )

                }
                else -> {
                    viewModelServer.postUserPhones(
                        item.phone,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        "Коллектор",
                        "Bearer ${value!!}"
                    )
                }
            }
        }
    }

    private fun init() {
        with(binding) {
            RcView.layoutManager = LinearLayoutManager(requireContext())
            RcView.adapter = adapter
        }
    }

    @SuppressLint("Range")
    fun initContact() {
        var arrayContatct = arrayListOf<Item>()
        val cursor = requireActivity().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val fullName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newModel = Item("", "")
                newModel.name = fullName.toString()
                newModel.phone = phone.replace(Regex("[\\s,-]"), "")
                arrayContatct.add(newModel)
            }
            arrayContatct = arrayContatct.distinct() as ArrayList<Item>
        }
        adapter.addRepoz(arrayContatct)
        cursor?.close()
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}