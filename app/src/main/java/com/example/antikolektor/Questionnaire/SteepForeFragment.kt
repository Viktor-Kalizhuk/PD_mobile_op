package com.example.antikolektor.Questionnaire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentSteepForeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SteepForeFragment : Fragment() {
private val dlogExit= DialogExitQuestionareFragment()
    lateinit var binding: FragmentSteepForeBinding
    lateinit var dataStore: DataStore<Preferences>
    val viewModelServer: StepForeViewModel by lazy {
        ViewModelProvider(this).get(StepForeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSteepForeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getProfile("Bearer ${value}")
        }
        viewModelServer.profileByLiveData.observe(viewLifecycleOwner, Observer { response ->
            if (response == null) {
                Toast.makeText(requireContext(), "запрос пуст", Toast.LENGTH_SHORT).show()
            } else {
                if (response.profile.first().passport_series != null) {
                    binding.textEdit1.setText(response.profile.first().passport_series)
                }
                if (response.profile.first().passport_numb != null) {
                    binding.textEdit2.setText(response.profile.first().passport_numb)
                }
                if (response.profile.first().passport_issued_by != null) {
                    binding.textEdit3.setText(response.profile.first().passport_issued_by)
                }
                if (response.profile.first().passport_date_of_issue != null) {
                    binding.textEdit4.setText(response.profile.first().passport_date_of_issue)
                }
                if (response.profile.first().passport_department_code != null) {
                    binding.textEdit5.setText(response.profile.first().passport_department_code)
                }
                if (response.profile.first().place_of_birth != null) {
                    binding.textEdit6.setText(response.profile.first().place_of_birth)
                }
                if (response.profile.first().address != null) {
                    binding.textEdit7.setText(response.profile.first().address)
                }
                if (response.profile.first().actual_address != null) {
                    binding.textEdit8.setText(response.profile.first().actual_address)
                }

//                if (response.profile.first().family_status != null) {
//                    val list = response.profile.first().family_status.toString().split(";")
//                    list.forEach {
//                        when (it.toString().lowercase()) {
//                            binding.TextView1.text -> {
//                                binding.CheckBox1.isChecked = true
//                            }
//                            binding.TextView2.text -> {
//                                binding.CheckBox2.isChecked = true
//                            }
//                            binding.TextView3.text -> {
//                                binding.CheckBox3.isChecked = true
//                            }
//                            binding.TextView4.text -> {
//                                binding.CheckBox4.isChecked = true
//                            }
//                        }
//                    }
//                }
            }
        })
        binding.ButtonExit.setOnClickListener {
            dlogExit.show(requireActivity().supportFragmentManager,"ExitQuestionare")
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}