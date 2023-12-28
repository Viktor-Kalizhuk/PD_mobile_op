package com.example.antikolektor.More.SetingProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R
import com.example.antikolektor.databinding.ExitDialogBinding
import kotlinx.coroutines.launch


class SettingDialogFragment : DialogFragment() {
    lateinit var dataStore: DataStore<Preferences>
    lateinit var binding: ExitDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExitDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")

        binding.dont.setOnClickListener {
            dialog?.dismiss()
        }
        binding.exit.setOnClickListener {
            lifecycleScope.launch {
                save(
                    "qwe", ""

                )
            }
            findNavController().navigate(R.id.action_setingProfileMainFragment_to_startFragment)
            dialog?.dismiss()
        }

    }

    private suspend fun save(key: String, value: String) {
        val dataStorekey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStorekey] = value
        }
    }
}