package com.example.antikolektor.Questionnaire

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentStepOneBinding
import com.example.antikolektor.putProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*


class StepOneFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>

    var id: Int? = null
    var gender: String? = null
    val viewModelServer: StepOnewViewModel by lazy {
        ViewModelProvider(this)[StepOnewViewModel::class.java]
    }
    lateinit var binding: FragmentStepOneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepOneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")

            viewModelServer.getProfile("Bearer $value")
        }
        viewModelServer.directoryByLiveData.observe(viewLifecycleOwner) { response ->
            val list = arrayListOf<String>()
            response!!.suggestions.forEach {
                list.add(it.value.toString())
            }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)

            binding.autoCompleteTextView.setAdapter(arrayAdapter)
        }
        viewModelServer.profileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == null) {
                Toast.makeText(requireContext(), "запрос пуст", Toast.LENGTH_SHORT).show()
            } else {
                id = response.id

                if (response.last_name.isNullOrEmpty()) {

                } else {
                    binding.lastName.setText(response.last_name.toString())
                }

                if (response.first_name.isNullOrEmpty()) {
                } else {
                    binding.firstName.setText(response.first_name.toString())
                }

                if (response.middle_name.isNullOrEmpty()) {

                } else {
                    binding.middleName.setText(response.middle_name.toString())
                }

                if (response.email.isNullOrEmpty()) {

                } else {

                    binding.email.setText(response.email.toString())
                }

                if (response.profile.first().birthday.isNullOrEmpty()) {
                } else {
                    binding.birthday.setText(response.profile.first().birthday)
                }

                if (response.profile.first().address.isNullOrEmpty()) {

                } else {
                    binding.autoCompleteTextView.setText(response.profile.first().address.toString())
                }
                if (response.profile.first().gender.isNullOrEmpty()) {

                } else {

                    if (response.profile.first().gender.toString() == "Мужской") {
                        gender = response.profile.first().gender.toString()
                        binding.male.isChecked = true
                    } else {
                        gender = response.profile.first().gender.toString()
                        binding.fem.isChecked = true
                    }
                }
            }
            binding.firstName.addTextChangedListener(watcher())
            binding.lastName.addTextChangedListener(watcher())
            binding.middleName.addTextChangedListener(watcher())
            binding.email.addTextChangedListener(watcher())
            binding.birthday.addTextChangedListener(watcher())
            binding.autoCompleteTextView.addTextChangedListener(watcherAddres())
        }



        binding.gender.setOnCheckedChangeListener { _, checked_id ->
            Toast.makeText(requireContext(), checked_id.toString(), Toast.LENGTH_SHORT).show()
            if (checked_id == binding.male.id) {
                Toast.makeText(requireContext(), checked_id.toString(), Toast.LENGTH_SHORT).show()
                gender = "Мужской"
                addDataProfile(
                    id,
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.middleName.text.toString(),
                    binding.email.text.toString(),
                    binding.autoCompleteTextView.text.toString(),
                    binding.birthday.text.toString(),
                    gender
                )
            } else {
                gender = "Женский"
                addDataProfile(
                    id,
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.middleName.text.toString(),
                    binding.email.text.toString(),
                    binding.autoCompleteTextView.text.toString(),
                    binding.birthday.text.toString(),
                    gender
                )
            }
        }
        binding.birthday.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(), { view, year, monthOfYear, dayOfMonth ->
                    var monthYear: String? = null
                    if (monthOfYear + 1 < 10) {
                        monthYear = "0${monthOfYear + 1}"
                        val dat = ("$dayOfMonth.$monthYear.$year")
                        binding.birthday.setText(dat)
                    } else {
                        val dat = (dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year)
                        binding.birthday.setText(dat)
                    }

                },

                year, month, day
            )
            datePickerDialog.show()
        }

        binding.button.setOnClickListener {
            if (binding.firstName.text.toString().isNotEmpty() && binding.lastName.text.toString()
                    .isNotEmpty() && binding.middleName.text.toString()
                    .isNotEmpty() && binding.autoCompleteTextView.text.toString()
                    .isNotEmpty() && binding.birthday.text.toString()
                    .isNotEmpty() && gender!!.isNotEmpty()
            ) {
                val result = "stepTwo"
                requireActivity().supportFragmentManager.setFragmentResult(
                    "request", bundleOf("extra" to result)
                )
            } else {
                if (binding.firstName.text.toString().isEmpty()) {
                    binding.FirstName.error = " "
                } else {
                    binding.FirstName.error = null
                }

                if (binding.lastName.text.toString().isEmpty()) {
                    binding.LastName.error = " "
                } else {
                    binding.LastName.error = null
                }

                if (binding.middleName.text.toString().isEmpty()) {
                    binding.MiddleName.error = " "
                } else {
                    binding.MiddleName.error = null
                }

                if (binding.autoCompleteTextView.text.toString().isEmpty()) {
                    binding.AutoCompleteTextView.error = " "
                } else {
                    binding.AutoCompleteTextView.error = null
                }

                if (binding.birthday.text.toString().isEmpty()) {
                    binding.Birthday.error = " "
                } else {
                    binding.Birthday.error = null
                }
            }
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }

    private fun watcher(): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            addDataProfile(
                id,
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.middleName.text.toString(),
                binding.email.text.toString(),
                binding.autoCompleteTextView.text.toString(),
                binding.birthday.text.toString(),
                gender
            )
        }
    }

    fun watcherAddres(): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            viewModelServer.postDirectory(
                s.toString(), "Token b2a9870befd6d61efd933591ca1c3f8fcfbb1263"
            )
            addDataProfile(
                id,
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.middleName.text.toString(),
                binding.email.text.toString(),
                binding.autoCompleteTextView.text.toString(),
                binding.birthday.text.toString(),
                gender
            )
        }
    }

    fun addDataProfile(
        id: Int?,
        first_name: String,
        last_name: String?,
        middle_name: String?,
        email: String?,
        address: String?,
        birthday: String?,
        gender: String?
    ) {
        if (email.toString().isEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.updateProfile(
                    id!!.toInt(),
                    putProfile(first_name, last_name, middle_name, " ", address, birthday, gender),
                    "Bearer $value"
                )
            }
//            Log.d("first_name",first_name.toString())
//            Log.d("lastName",last_name.toString())
//            Log.d("middleName",middle_name.toString())
//            Log.d("email",email.toString())
//            Log.d("autoCompleteTextView",address.toString())
//            Log.d("birthday",birthday.toString())
//            Log.d("gender",gender.toString())
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.updateProfile(
                    id!!.toInt(), putProfile(
                        first_name, last_name, middle_name, email, address, birthday, gender
                    ), "Bearer $value"
                )
            }
        }
    }

}