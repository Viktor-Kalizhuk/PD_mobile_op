package com.example.antikolektor.More.AntiKolektor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.antikolektor.R
import com.example.antikolektor.databinding.AddBlackListDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AddBlackListDialogFragment : DialogFragment() {
    val viewModelServer: ViewModelBlackList by lazy {
        ViewModelProvider(this)[ViewModelBlackList::class.java]
    }
    lateinit var dataStore: DataStore<Preferences>
    lateinit var binding: AddBlackListDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddBlackListDialogBinding.inflate(layoutInflater)
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
        val feelings = resources.getStringArray(R.array.feelings)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, feelings)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.ButtonYes.setOnClickListener {
            val phone = ("7" + binding.EdText.text.toString())
            val type = binding.autoCompleteTextView.text.toString()

            if (phone.length > 11 || type.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.postUserPhones(
                        phone,
                        "Какой то комментарий к телефону",
                        "Черный список",
                        type,
                        "Bearer $value"
                    )
                }
                val result = "update"
                requireActivity().supportFragmentManager
                    .setFragmentResult("request_key", bundleOf("extra_key" to result))

                dialog?.dismiss()
            }else{
                if (phone.length < 11) {
                    binding.EdText.error = ""
                }
                if (type.isEmpty()) {
                    binding.autoCompleteTextView.error = ""
                }
            }


        }
        binding.ButtonNo.setOnClickListener {
            dialog?.dismiss()
        }

    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}