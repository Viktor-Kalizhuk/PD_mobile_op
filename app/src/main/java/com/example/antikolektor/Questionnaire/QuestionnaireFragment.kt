package com.example.antikolektor.Questionnaire

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.R

import com.example.antikolektor.databinding.DialogSanckBinding
import com.example.antikolektor.databinding.FragmentQuestionnaireBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class QuestionnaireFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    var selectedSort: String? = null
    lateinit var binding: FragmentQuestionnaireBinding
    val viewModelServer: QuestionaireViewModel by lazy {
        ViewModelProvider(this)[QuestionaireViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionnaireBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "request", requireActivity()
        ) { _, bundle ->
            selectedSort = bundle.getString("extra")
            when (selectedSort) {
                "stepTwo" -> {
                    getDataProfile()
                }
                "stepThree" -> {
                    getDataProfile()
                }
                "exit" -> {
                    dialog()
                }
            }
        }
        binding.linearLayout6.setOnClickListener {
            supportFragment(StepOneFragment())
        }
        binding.linearLayout7.setOnClickListener {
            supportFragment(SteepForeFragment())
        }
        binding.linearLayout8.setOnClickListener {
            supportFragment(StepThreeFragment())
        }
        binding.linearLayout9.setOnClickListener {
            supportFragment(StepTwoFragment())
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_questionnaireFragment_to_personalAreaFragment)
        }
        getDataProfile()
    }


    fun getDataProfile() {
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getProfile("Bearer $value")
        }
        readDataProfile()
    }


    fun readDataProfile() {
        viewModelServer.profileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.profile.first().gender == null || response.last_name == null || response.first_name == null || response.middle_name == null || response.profile.first().address == null) {
                    binding.linearLayout9.isEnabled = false
                    binding.linearLayout7.isEnabled = false
                    binding.linearLayout8.isEnabled = false
                    binding.progressFore.setBackgroundResource(R.color.text3)
                    binding.progressTwo.setBackgroundResource(R.color.text3)
                    binding.progressThree.setBackgroundResource(R.color.text3)
                    binding.TvTwo.setTextColor(requireContext().getColor(R.color.text3))
                    binding.TvFore.setTextColor(requireContext().getColor(R.color.text3))
                    binding.TvThree.setTextColor(requireContext().getColor(R.color.text3))
                    supportFragment(StepOneFragment())
                } else {
                    if (response.profile.first().reason_for_delays == null || response.profile.first().reason_for_delays == "null" || response.profile.first().nature_of_work == null || response.profile.first().total_income == null || response.profile.first().additional_income == null || response.profile.first().additional_income_amount == null || response.profile.first().cost == null || response.profile.first().cost_amount == null || response.profile.first().monthly_payment == null) {
                        binding.linearLayout8.isEnabled = false
                        binding.linearLayout7.isEnabled = false
                        binding.progressTwo.setBackgroundResource(R.color.text3)
                        binding.progressFore.setBackgroundResource(R.color.text3)
                        binding.progressThree.setBackgroundResource(R.color.text3)
                        binding.TvTwo.setTextColor(requireContext().getColor(R.color.text3))
                        binding.TvFore.setTextColor(requireContext().getColor(R.color.text3))
                        binding.TvThree.setTextColor(requireContext().getColor(R.color.text3))
                        supportFragment(StepTwoFragment())
                    } else {
                        if (response.profile.first().reason_for_delays != null && response.profile.first().nature_of_work != null && response.profile.first().total_income != null && response.profile.first().additional_income != null && response.profile.first().additional_income_amount != null && response.profile.first().cost != null && response.profile.first().cost_amount != null && response.profile.first().monthly_payment != null && response.profile.first().gender != null && response.last_name != null && response.first_name != null && response.middle_name != null && response.email != null && response.profile.first().address != null && response.credit.bank.isNullOrEmpty()) {
                            binding.linearLayout7.isEnabled = false
                            binding.progressFore.setBackgroundResource(R.color.text3)
                            binding.TvFore.setTextColor(requireContext().getColor(R.color.text3))
                            binding.TvThree.setTextColor(requireContext().getColor(R.color.text3))
                            binding.progressThree.setBackgroundResource(R.color.text3)
                            supportFragment(StepThreeFragment())
                        } else {
                            if (response.program == true) {
                                binding.linearLayout7.visibility = View.VISIBLE
                                binding.progressFore.setBackgroundResource(R.color.text3)
                                binding.TvFore.setTextColor(requireContext().getColor(R.color.text3))
                                supportFragment(SteepForeFragment())
                            } else {
                                if (response.program == false) {
                                    supportFragment(StepThreeFragment())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun supportFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, fragment).commit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("InflateParams")
    fun dialog() {
        val dialogBinding =
            DialogSanckBinding.bind(layoutInflater.inflate(R.layout.dialog_sanck, null))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialogBinding.ButtonYes.setOnClickListener {
            findNavController().navigate(R.id.action_questionnaireFragment_to_personalAreaFragment)
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}