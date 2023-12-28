package com.example.antikolektor.Questionnaire


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.antikolektor.databinding.FragmentStepTwoBinding
import com.example.antikolektor.putProfileStepTwo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class StepTwoFragment : Fragment() {
    lateinit var dataStore: DataStore<Preferences>
    var id: Int? = null
    val viewModelServer: StepTwoViewModel by lazy {
        ViewModelProvider(this)[StepTwoViewModel::class.java]
    }
    private var reason_for_delays: String? = null
    private var nature_of_work: String? = null
    private var additional_income: String? = null
    private var cost: String? = null
    private lateinit var binding: FragmentStepTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepTwoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getProfile("Bearer $value")
        }
        viewModelServer.profileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == null) {
                Toast.makeText(requireContext(), "запрос пуст", Toast.LENGTH_SHORT).show()
            } else {
                id = response.id
                if (response.profile.first().reason_for_delays != null) {
                    reason_for_delays = response.profile.first().reason_for_delays.toString()
                    val list = response.profile.first().reason_for_delays.toString().split(";")
                    list.forEach {
                        when (it.lowercase()) {
                            binding.Tv1.text.toString() -> {
                                binding.check1.isChecked = true
                            }
                            binding.Tv2.text.toString() -> {
                                binding.check2.isChecked = true
                            }
                            binding.Tv3.text.toString() -> {
                                binding.check3.isChecked = true
                            }
                            binding.Tv4.text.toString() -> {
                                binding.check4.isChecked = true
                            }
                            binding.Tv5.text.toString() -> {
                                binding.check5.isChecked = true
                            }
                            binding.Tv6.text.toString() -> {
                                binding.check6.isChecked = true
                            }
                            binding.Tv7.text.toString() -> {
                                binding.check7.isChecked = true
                            }
                            binding.Tv8.text.toString() -> {
                                binding.check8.isChecked = true
                            }
                            binding.Tv9.text.toString() -> {
                                binding.check9.isChecked = true
                            }
                            binding.Tv10.text.toString() -> {
                                binding.check10.isChecked = true
                            }
                            binding.Tv11.text.toString() -> {
                                binding.check11.isChecked = true
                            }
                            binding.Tv12.text.toString() -> {
                                binding.check12.isChecked = true
                            }
                        }
                    }
                }
                if (response.profile.first().nature_of_work != null) {
                    nature_of_work = response.profile.first().nature_of_work.toString()
                    val list = response.profile.first().nature_of_work.toString().split(";")
                    list.forEach {
                        when (it.lowercase()) {
                            binding.Tv13.text.toString() -> {
                                binding.check13.isChecked = true
                            }
                            binding.Tv14.text.toString() -> {
                                binding.check14.isChecked = true
                            }
                            binding.Tv15.text.toString() -> {
                                binding.check15.isChecked = true
                            }
                            binding.Tv16.text.toString() -> {
                                binding.check16.isChecked = true
                            }
                            binding.Tv17.text.toString() -> {
                                binding.check17.isChecked = true
                            }
                        }
                    }
                }

                if (response.profile.first().total_income != null) {
                    binding.TextEdit1.setText(response.profile.first().total_income)
                }
                if (response.profile.first().additional_income != null) {
                    additional_income = response.profile.first().additional_income.toString()
                    val list = response.profile.first().additional_income.toString().split(";")
                    list.forEach {
                        when (it.lowercase()) {
                            binding.Tv18.text.toString() -> {
                                binding.check18.isChecked = true
                            }
                            binding.Tv19.text.toString() -> {
                                binding.check19.isChecked = true
                            }
                            binding.Tv20.text.toString() -> {
                                binding.check20.isChecked = true
                            }
                            binding.Tv21.text.toString() -> {
                                binding.check21.isChecked = true
                            }
                            binding.Tv22.text.toString() -> {
                                binding.check22.isChecked = true
                            }
                        }
                    }
                }
                if (response.profile.first().additional_income_amount != null) {
                    binding.TextEdit2.setText(response.profile.first().additional_income_amount)

                }
                if (response.profile.first().cost != null) {
                    cost = response.profile.first().cost.toString()
                    val list = response.profile.first().cost.toString().split(";")
                    list.forEach {
                        when (it.lowercase()) {
                            binding.Tv23.text.toString() -> {
                                binding.check23.isChecked = true
                            }
                            binding.Tv24.text.toString() -> {
                                binding.check24.isChecked = true
                            }
                            binding.Tv25.text.toString() -> {
                                binding.check25.isChecked = true
                            }
                            binding.Tv26.text.toString() -> {
                                binding.check26.isChecked = true
                            }
                            binding.Tv27.text.toString() -> {
                                binding.check27.isChecked = true
                            }
                            binding.Tv28.text.toString() -> {
                                binding.check28.isChecked = true
                            }
                            binding.Tv29.text.toString() -> {
                                binding.check29.isChecked = true
                            }
                            binding.Tv30.text.toString() -> {
                                binding.check30.isChecked = true
                            }
                            binding.Tv31.text.toString() -> {
                                binding.check31.isChecked = true
                            }
                            binding.Tv32.text.toString() -> {
                                binding.check32.isChecked = true
                            }
                        }
                    }
                }
                if (response.profile.first().cost_amount != null) {
                    binding.TextEdit3.setText(response.profile.first().cost_amount)

                }
                if (response.profile.first().monthly_payment != null) {
                    binding.TextEdit4.setText(response.profile.first().monthly_payment)
                }
            }
            binding.TextEdit1.addTextChangedListener(watcher())
            binding.TextEdit2.addTextChangedListener(watcher())
            binding.TextEdit3.addTextChangedListener(watcher())
            binding.TextEdit4.addTextChangedListener(watcher())
        }

        binding.buttonNext.setOnClickListener {
            if (binding.TextEdit1.text.toString().isNotEmpty() && binding.TextEdit2.text.toString()
                    .isNotEmpty() && binding.TextEdit3.text.toString()
                    .isNotEmpty() && binding.TextEdit4.text.toString()
                    .isNotEmpty() && reason_for_delays.toString() != "null" && nature_of_work.toString() != "null" && additional_income.toString() != "null" && cost.toString() != "null" && reason_for_delays.toString()
                    .isNotEmpty() && nature_of_work.toString()
                    .isNotEmpty() && additional_income.toString().isNotEmpty() && cost.toString()
                    .isNotEmpty()
            ) {
                val result = "stepThree"
                requireActivity().supportFragmentManager.setFragmentResult(
                    "request", bundleOf("extra" to result)
                )
            } else {
                if (binding.TextEdit1.text.toString().isEmpty()) {
                    binding.TextInput1.error = " "
                } else {
                    binding.TextInput1.error = null
                }
                if (binding.TextEdit2.text.toString().isEmpty()) {
                    binding.TextInput2.error = " "
                } else {
                    binding.TextInput2.error = null
                }
                if (binding.TextEdit3.text.toString().isEmpty()) {
                    binding.TextInput3.error = " "
                } else {
                    binding.TextInput3.error = null
                }
                if (binding.TextEdit4.text.toString().isEmpty()) {
                    binding.TextInput4.error = " "
                } else {
                    binding.TextInput4.error = null
                }

                if (reason_for_delays.toString() == "null") {
                    binding.textErrorOne.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorOne.textError.visibility = View.GONE
                }
                if (nature_of_work.toString() == "null") {
                    binding.textErrorTwo.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorTwo.textError.visibility = View.GONE
                }
                if (additional_income.toString() == "null") {
                    binding.textErrorTree.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorTree.textError.visibility = View.GONE
                }
                if (cost.toString() == "null") {
                    binding.textErrorFore.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorFore.textError.visibility = View.GONE
                }
            }
        }

        binding.check1.setOnClickListener {
            addReasonForDelays(binding.check1, binding.Tv1)
        }
        binding.check2.setOnClickListener {
            addReasonForDelays(binding.check2, binding.Tv2)
        }
        binding.check3.setOnClickListener {
            addReasonForDelays(binding.check3, binding.Tv3)
        }
        binding.check4.setOnClickListener {
            addReasonForDelays(binding.check4, binding.Tv4)
        }
        binding.check5.setOnClickListener {
            addReasonForDelays(binding.check5, binding.Tv5)
        }
        binding.check6.setOnClickListener {
            addReasonForDelays(binding.check6, binding.Tv6)
        }
        binding.check7.setOnClickListener {
            addReasonForDelays(binding.check7, binding.Tv7)
        }
        binding.check8.setOnClickListener {
            addReasonForDelays(binding.check8, binding.Tv8)
        }
        binding.check9.setOnClickListener {
            addReasonForDelays(binding.check9, binding.Tv9)
        }
        binding.check10.setOnClickListener {
            addReasonForDelays(binding.check10, binding.Tv10)
        }
        binding.check11.setOnClickListener {
            addReasonForDelays(binding.check11, binding.Tv11)
        }
        binding.check12.setOnClickListener {
            addReasonForDelays(binding.check12, binding.Tv12)
        }



        binding.check13.setOnClickListener {
            addNatureOfWork(binding.check13, binding.Tv13)
        }
        binding.check14.setOnClickListener {
            addNatureOfWork(binding.check14, binding.Tv14)
        }
        binding.check15.setOnClickListener {
            addNatureOfWork(binding.check15, binding.Tv15)
        }
        binding.check16.setOnClickListener {
            addNatureOfWork(binding.check16, binding.Tv16)
        }
        binding.check17.setOnClickListener {
            addNatureOfWork(binding.check17, binding.Tv17)
        }


        binding.check18.setOnClickListener {
            addAdditionalIncome(binding.check18, binding.Tv18)
        }
        binding.check19.setOnClickListener {
            addAdditionalIncome(binding.check19, binding.Tv19)
        }
        binding.check20.setOnClickListener {
            addAdditionalIncome(binding.check20, binding.Tv20)
        }
        binding.check21.setOnClickListener {
            addAdditionalIncome(binding.check21, binding.Tv21)
        }
        binding.check22.setOnClickListener {
            addAdditionalIncome(binding.check22, binding.Tv22)
        }


        binding.check23.setOnClickListener {
            addCost(binding.check23, binding.Tv23)
        }
        binding.check24.setOnClickListener {
            addCost(binding.check24, binding.Tv24)
        }
        binding.check25.setOnClickListener {
            addCost(binding.check25, binding.Tv25)
        }
        binding.check26.setOnClickListener {
            addCost(binding.check26, binding.Tv26)
        }
        binding.check27.setOnClickListener {
            addCost(binding.check27, binding.Tv27)
        }
        binding.check28.setOnClickListener {
            addCost(binding.check28, binding.Tv28)
        }
        binding.check29.setOnClickListener {
            addCost(binding.check29, binding.Tv29)
        }
        binding.check30.setOnClickListener {
            addCost(binding.check30, binding.Tv30)
        }
        binding.check31.setOnClickListener {
            addCost(binding.check31, binding.Tv31)
        }
        binding.check32.setOnClickListener {
            addCost(binding.check32, binding.Tv32)
        }

    }

    fun addDataProfile(
        total_income: String,
        additional_income_amount: String,
        cost_amount: String,
        monthly_payment: String,
        reason_for_delays: String,
        nature_of_work: String,
        additional_income: String,
        cost: String
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.updateProfile(
                id!!.toInt(), putProfileStepTwo(
                    total_income,
                    additional_income_amount,
                    cost_amount,
                    monthly_payment,
                    reason_for_delays,
                    nature_of_work,
                    additional_income,
                    cost
                ), "Bearer $value"
            )
        }
    }

    private fun watcher(): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()
            )

        }
    }

    private fun addReasonForDelays(checkBox: CheckBox, textView: TextView) {
        if (checkBox.isChecked == true) {
            val text = textView.text
            reason_for_delays += ";${text}"
            Log.d("deb", reason_for_delays.toString())

            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()

            )
        } else {
            val text = textView.text
            reason_for_delays = reason_for_delays.toString().replace(";${text}", "")
            Log.d("deb", reason_for_delays.toString())

            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()
            )
        }
    }

    fun addNatureOfWork(checkBox: CheckBox, textView: TextView) {
        if (checkBox.isChecked == true) {
            val text = textView.text
            nature_of_work += ";${text}"
            Log.d("deb", nature_of_work.toString())

            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()

            )
        } else {
            val text = textView.text
            nature_of_work = nature_of_work.toString().replace(";${text}", "")
            Log.d("deb", nature_of_work.toString())
            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()
            )
        }
    }


    fun addAdditionalIncome(checkBox: CheckBox, textView: TextView) {
        if (checkBox.isChecked == true) {
            val text = textView.text
            additional_income += ";${text}"
            Log.d("deb", additional_income.toString())

            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()

            )
        } else {
            val text = textView.text
            additional_income = additional_income.toString().replace(";${text}", "")
            Log.d("deb", additional_income.toString())
//                Toast.makeText(requireContext(), reason_for_delays, Toast.LENGTH_SHORT).show()
            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()
            )
        }
    }

    fun addCost(checkBox: CheckBox, textView: TextView) {
        if (checkBox.isChecked == true) {
            val text = textView.text
            cost += ";${text}"
            Log.d("deb", cost.toString())

            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()

            )
        } else {
            val text = textView.text
            cost = cost.toString().replace(";${text}", "")
            Log.d("deb", cost.toString())
//                Toast.makeText(requireContext(), reason_for_delays, Toast.LENGTH_SHORT).show()
            addDataProfile(
                binding.TextEdit1.text.toString(),
                binding.TextEdit2.text.toString(),
                binding.TextEdit3.text.toString(),
                binding.TextEdit4.text.toString(),
                reason_for_delays.toString(),
                nature_of_work.toString(),
                additional_income.toString(),
                cost.toString()
            )
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }

}