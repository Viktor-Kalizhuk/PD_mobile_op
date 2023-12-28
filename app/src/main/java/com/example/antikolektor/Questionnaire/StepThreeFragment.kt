package com.example.antikolektor.Questionnaire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.AdapterBank
import com.example.antikolektor.Bank
import com.example.antikolektor.Files
import com.example.antikolektor.databinding.FragmentStepThreeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class StepThreeFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>

    val adapter = AdapterBank { callback1(it) }
    val viewModelServer: StepThreeViewModel by lazy {
        ViewModelProvider(this)[StepThreeViewModel::class.java]
    }
    lateinit var binding: FragmentStepThreeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepThreeBinding.inflate(layoutInflater)

        return binding.root
    }


    fun callback1(it: Bank) {
        findNavController().navigate(
            QuestionnaireFragmentDirections.actionQuestionnaireFragmentToAddLenderFragment(
                "edit",
                it
            )
        )
    }

    fun init() {
        with(binding) {
            RcView.layoutManager = LinearLayoutManager(requireContext())
            RcView.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        init()
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getProfile("Bearer $value")
        }
        viewModelServer.profileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response!!.credit.bank.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "запрос пуст или не имеет данных о кредиторе",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                adapter.addRepoz(response.credit.bank)

            }
        }
        binding.button.setOnClickListener {
            val result = "exit"
            requireActivity().supportFragmentManager
                .setFragmentResult("request", bundleOf("extra" to result))
        }
        binding.buttonAdd.setOnClickListener {
            Toast.makeText(requireContext(), "3", Toast.LENGTH_SHORT).show()
            val file = arrayListOf<Files>()
            findNavController().navigate(
                QuestionnaireFragmentDirections.actionQuestionnaireFragmentToAddLenderFragment(
                    "create",
                    Bank(0,0,0,"","","","","",
                    "","","","","","","",
                    "","","","","",file)
                )
            )
        }

    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }

}