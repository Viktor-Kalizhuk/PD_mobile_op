package com.example.antikolektor.Autarization

import android.R
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.antikolektor.Server.ViewModel.SharedViewModel
import com.example.antikolektor.databinding.FragmentEntrySmsBinding
import kotlinx.coroutines.launch


class EntrySMSFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    lateinit var binding: FragmentEntrySmsBinding
    val args: EntrySMSFragmentArgs by navArgs()
    lateinit var code: String
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntrySmsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.textView50.text = (binding.textView50.text.toString() + args.phone.toString())
        timer()
        viewModel.regenerateCode(args.phone)
        binding.button3.setOnClickListener {
            timer()
            viewModel.regenerateCode(args.phone)
        }
        binding.button2.setOnClickListener {
            viewModel.refreshToken(args.phone, binding.pinView.text.toString())
        }
        viewModel.regenerateCodeByIdLiveData.observe(requireActivity()) { response ->
            if (response == null) {
                Toast.makeText(requireContext(), "Unsuccesful necvork call", Toast.LENGTH_LONG)
                    .show()
                return@observe
            }
            code = response.toString()
        }

        viewModel.tokenByIdLiveData.observe(requireActivity()) { response ->
            if (response == null) {
                binding.pinView.error = "error"
                Toast.makeText(requireContext(), "неверный пароль", Toast.LENGTH_LONG).show()
                return@observe
            } else {
                lifecycleScope.launch {
                    save(
                        "qwe", response
                    )
                    findNavController().navigate(com.example.antikolektor.R.id.action_entrySMSFragment_to_entryStorisFragment)
                }

            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                EntrySMSFragmentDirections.actionEntrySMSFragmentToEntryPaswordFragment(
                    args.code,
                    args.phone
                )
            )
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(
                    EntrySMSFragmentDirections.actionEntrySMSFragmentToEntryPaswordFragment(
                        args.code,
                        args.phone
                    )
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    fun timer() {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.button3.setEnabled(false)
                binding.textTimer.setText("  " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                binding.button3.setEnabled(true)
                binding.textTimer.setText("")
            }
        }.start()
    }

    private suspend fun save(key: String, value: String) {
        val dataStorekey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStorekey] = value
        }
    }
}
