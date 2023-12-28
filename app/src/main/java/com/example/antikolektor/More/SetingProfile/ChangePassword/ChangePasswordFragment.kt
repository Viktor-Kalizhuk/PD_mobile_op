package com.example.antikolektor.More.SetingProfile.ChangePassword

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
import com.example.antikolektor.Adapter.AdapterPhone
import com.example.antikolektor.More.SetingProfile.AddPhone.MyPhoneViewModel
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentChangePasswordBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ChangePasswordFragment : Fragment() {
    lateinit var binding: FragmentChangePasswordBinding

    lateinit var dataStore: DataStore<Preferences>
    val viewModelServer: ChangePasswordViewModel by lazy {
        ViewModelProvider(this)[ChangePasswordViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = requireContext().createDataStore(name = "settings")

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_changePassword_to_setingProfileMainFragment)
        }
        binding.ButtonSave.setOnClickListener {
            examination(
                binding.TextEditPass.text.toString(),
                binding.TextEditPassNew.text.toString()
            )
        }
        viewModelServer.changeCodeByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == "OK") {
                Snackbar.make(binding.ConsRoot, "Пароль сохранен", Snackbar.LENGTH_LONG).show()
            }

        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_changePassword_to_setingProfileMainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    fun examination(text1: String, text2: String) {
        if (text1.isNotEmpty() && text2.isNotEmpty() && text2 == text1) {
            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.changeCode(text1, "Bearer $value")
            }
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}