package com.example.antikolektor.More.AntiKolektor

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.Adapter
import com.example.antikolektor.DataRecycler
import com.example.antikolektor.R
import com.example.antikolektor.RoomDatabase.Base
import com.example.antikolektor.RoomDatabase.User
import com.example.antikolektor.databinding.FragmentBlackListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



class BlackListFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    private var selectedSort: String? = null
    private val recyclerAdapter = Adapter { callback1(it) }
    private val addBlackList = AddBlackListBotomShitFragment()
    lateinit var binding: FragmentBlackListBinding
    val viewModelServer: ViewModelBlackList by lazy {
        ViewModelProvider(this)[ViewModelBlackList::class.java]
    }

    fun callback1(it: DataRecycler) {
        lifecycleScope.launch {
            val value = readToken("qwe")
            recyclerAdapter.removeItem(it.position)
            viewModelServer.deleteUserPhones(it.id, "Bearer ${value!!}")
            viewModelServer.deleteUser(User(it.id, it.type, it.phone, it.subtype, it.comment))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlackListBinding.inflate(layoutInflater)
        return binding.root
    }

    fun init() {
        with(binding) {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = recyclerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        init()
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.baseUserPhones("Bearer ${value!!}") //список телефонов пользователя
        }
               printBasePhone()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "request_key", requireActivity()
        ) { _, bundle ->
            selectedSort = bundle.getString("extra_key")
            when (selectedSort) {
                "dialog" -> {
                    findNavController().navigate(R.id.addBlackListDialogFragment)
                }
                "update" -> {
                    Toast.makeText(requireContext(), "обновить", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch(Dispatchers.IO) {
                        val value = readToken("qwe")
                        viewModelServer.baseUserPhones("Bearer ${value!!}") //список телефонов пользователя
                    }
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            addBlackList.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme)
            addBlackList.show(requireActivity().supportFragmentManager, "TaG")
        }

        viewModelServer.baseUserPhonesByIdLiveData.observe(
            viewLifecycleOwner
        ) { responseUser ->
            if (responseUser?.data.isNullOrEmpty()) {
                binding.ConsNone.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Нет номеров", Toast.LENGTH_LONG).show()
            } else {
                val listUserData = mutableListOf<com.example.antikolektor.Server.Data>()
                binding.ConsNone.visibility = View.GONE
                listUserData.addAll(responseUser!!.data)
                listUserData.reverse()
                recyclerAdapter.addRepoz(listUserData)
                responseUser.data.forEach {
                    insertPhoneUserDataBase(it.id, it.type, it.phone, it.subtype, it.comment!!)
                }
            }
        }
    }

    //получение базовых номеров пользователя
    private fun printBasePhone() {
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.basePhones("Bearer ${value!!}") //список телефонов пользователя
        }
        viewModelServer.basePhonesByIdLiveData.observe(viewLifecycleOwner) { response ->
            if (response?.data.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "ошибка", Toast.LENGTH_LONG).show()
            } else {
                response?.data?.forEach {
                    insetBasetoDataBase(
                        it.id, it.type, it.phone, it.subtype, it.comment.toString()
                    )
                }
            }
        }
    }

    private fun insertPhoneUserDataBase(
        id: Int, type: String, phone: String, subtype: String, comment: String
    ) {
        if (inputCheckUser(type, phone, subtype, comment)) {
            val user = User(id, type, phone, subtype, comment)
            viewModelServer.addUser(user)
        }
    }

    private fun inputCheckUser(
        type: String, phone: String, subtype: String, comment: String
    ): Boolean {
        return !(TextUtils.isEmpty(type) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(subtype) && TextUtils.isEmpty(
            comment
        ))
    }

    //добавление базового списка
    private fun insetBasetoDataBase(
        id: Int, type: String, phone: String, subtype: String, comment: String
    ) {
        if (inputCheck(phone, subtype)) {
            val user = Base(
                id, type, phone, subtype, comment
            )
            //Add Data to Database
            viewModelServer.addBase(user)
        }
    }

    private fun inputCheck(phone: String, subtype: String): Boolean {
        return !(TextUtils.isEmpty(phone) && TextUtils.isEmpty(subtype))
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}