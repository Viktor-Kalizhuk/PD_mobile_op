package com.example.antikolektor.Autarization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.antikolektor.R
import com.example.antikolektor.Server.ViewModel.SharedViewModel
import com.example.antikolektor.databinding.FragmentEntryPaswordBinding

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EntryPaswordFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    lateinit var binding: FragmentEntryPaswordBinding
    val args: EntryPaswordFragmentArgs by navArgs()
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryPaswordBinding.inflate(layoutInflater)
        return (binding.root)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.button2.setOnClickListener {
            if (args.code.toString() == binding.pinView.text.toString()) {
                viewModel.refreshToken(args.phone, binding.pinView.text.toString())
            }
        }
        binding.button3.setOnClickListener {

            findNavController().navigate(
                EntryPaswordFragmentDirections.actionEntryPaswordFragmentToEntrySMSFragment(
                    args.code,
                    args.phone
                )
            )
        }

        viewModel.tokenByIdLiveData.observe(requireActivity()) { response ->
            if (response == null) {
                binding.pinView.error = "error"
                Toast.makeText(requireContext(), "неверный пароль", Toast.LENGTH_LONG).show()
                return@observe
            } else {
                lifecycleScope.launch{
                    save(
                        "qwe",response
                    )
                    findNavController().navigate(EntryPaswordFragmentDirections.actionEntryPaswordFragmentToEntryStorisFragment())
                }
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_entryPaswordFragment_to_entryPhoneFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                findNavController().navigate(R.id.action_entryPaswordFragment_to_entryPhoneFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)


    }
    private suspend fun save(key: String, value: String) {
        val dataStorekey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStorekey] = value
        }
    }

}