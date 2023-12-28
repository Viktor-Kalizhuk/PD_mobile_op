package com.example.antikolektor.Autarization


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.datastore.core.DataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentStartBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    lateinit var dataStore: DataStore<Preferences>
    lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater)

        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")

        binding.imageView.alpha = 0f
        binding.imageView.animate().setDuration(2000).alpha(1f).withEndAction {

            lifecycleScope.launch {
                val value = read("qwe")
                var param = value
                Toast.makeText(requireContext(), "qwweqqwe  ${value}", Toast.LENGTH_LONG).show()
                if (param.isNullOrEmpty()) {

                    findNavController().navigate(R.id.action_startFragment_to_entryPhoneFragment)
                } else {
                    findNavController().navigate(R.id.action_startFragment_to_personalAreaFragment)
                }

            }

        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private suspend fun read(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}