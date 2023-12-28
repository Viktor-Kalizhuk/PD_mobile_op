package com.example.antikolektor.More.ContactWithSpecialist

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.antikolektor.Payment.PaimentViewModel
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentContactWithSpecialistBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ContactWithSpecialistFragment : Fragment() {
    private lateinit var plauncher: ActivityResultLauncher<String>
    lateinit var binding: FragmentContactWithSpecialistBinding
    lateinit var dataStore: DataStore<Preferences>
    var phoneNo: String = ""
    val viewModelServer: ContactWhithSpecialistViewModel by lazy {
        ViewModelProvider(this)[ContactWhithSpecialistViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactWithSpecialistBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerPermishnListener()
        CheckPermishn()
        dataStore = requireContext().createDataStore(name = "settings")
        binding.feedBack.setOnClickListener {
            lifecycleScope.launch {
                val value = readToken("qwe")
                viewModelServer.getFeedBack("Bearer $value")
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getUserPanel("Bearer $value")
        }
        viewModelServer.feedBack.observe(viewLifecycleOwner) { observ ->
            Toast.makeText(
                requireContext(),
                "Ваша заявка получила статус ${observ.toString()}",
                Toast.LENGTH_LONG
            ).show()
        }

        viewModelServer.userPanel.observe(viewLifecycleOwner) { response ->
            if (response?.data != null) {
                if (response.data.first_name.isNullOrEmpty() && response.data.last_name.isNullOrEmpty() && response.data.middle_name.isNullOrEmpty()) {
                    binding.TvName.text ="Альберт Сергеевич"
                } else {
                    binding.TvName.text =
                        "${response.data.last_name} ${response.data.first_name} ${response.data.middle_name}"
                }
                if (response.data.photo.isNullOrEmpty()) {
                    Glide.with(requireContext())
                        .load(R.drawable.card)
                        .into(binding.IvAvatar)
                } else {
                    Glide.with(requireContext())
                        .load(response.data.photo)
                        .into(binding.IvAvatar)
                }
                if (response.data.phone.isNullOrEmpty()) {
                    binding.TvPhone.text = "${binding.TvPhone.text}" + " +78003330482"
                    phoneNo = "+78003330482"
                } else {
                    binding.TvPhone.text = "${binding.TvPhone.text}" + " ${response.data.phone}"
                    phoneNo = response.data.phone.toString()
                }
            }
        }


        binding.call.setOnClickListener {
            if (!TextUtils.isEmpty(phoneNo)) {
                val dial = "tel:$phoneNo"
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
            } else {
                Toast.makeText(requireContext(), "Enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_contactWithSpecialistFragment_to_moreFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_contactWithSpecialistFragment_to_moreFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun CheckPermishn() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_PHONE_STATE
            )
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(requireContext(), "Permishn Ok", Toast.LENGTH_LONG).show()

            }

            else -> {
                plauncher.launch(
                    Manifest.permission.READ_PHONE_STATE
                )

            }
        }
    }

    private fun registerPermishnListener() {
        plauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                if (it) {

                    Toast.makeText(requireContext(), "Permishn Ok", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Permishn denied", Toast.LENGTH_LONG).show()
                }
            }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}