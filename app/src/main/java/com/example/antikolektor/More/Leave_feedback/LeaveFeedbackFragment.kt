package com.example.antikolektor.More.Leave_feedback

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentLeaveFeedbackBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class LeaveFeedbackFragment : Fragment() {
    private val goMainDialog = LieaveDialogFragment()
    lateinit var dataStore: DataStore<Preferences>
    val viewModelServer: LeaveFeedbackViewModel by lazy {
        ViewModelProvider(this)[LeaveFeedbackViewModel::class.java]
    }
    lateinit var binding: FragmentLeaveFeedbackBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaveFeedbackBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.button.setOnClickListener {
            if (binding.overalScore.rating.toInt() != 0 && binding.professionalismScore.rating.toInt() != 0 && binding.courtesyScore.rating.toInt() != 0 && binding.employeeFeedback.text.toString()
                    .isNotEmpty() && binding.companyFeedback.text.toString()
                    .isNotEmpty() && binding.helpFeedback.text.toString().isNotEmpty()
            ) {
                var improveFeedback = binding.improveFeedback.text.toString()
                if (improveFeedback.isEmpty()) {
                    improveFeedback = "null"
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.sendRating(
                        binding.overalScore.rating.toInt(),
                        binding.professionalismScore.rating.toInt(),
                        binding.courtesyScore.rating.toInt(),
                        binding.employeeFeedback.text.toString(),
                        binding.companyFeedback.text.toString(),
                        binding.helpFeedback.text.toString(),
                        improveFeedback,
                        "Bearer $value"
                    )
                    Log.d("overalScore", binding.overalScore.rating.toString())
                    Log.d("professionalismScore", binding.professionalismScore.rating.toString())
                    Log.d("courtesyScore", binding.courtesyScore.rating.toString())
                    Log.d("employeeFeedback", binding.employeeFeedback.text.toString())
                    Log.d("companyFeedback", binding.companyFeedback.text.toString())
                    Log.d("helpFeedback", binding.helpFeedback.text.toString())
                    Log.d("improveFeedback", improveFeedback)
                    Log.d("token", "Bearer $value")
                }
            } else {
                if (binding.overalScore.rating.toInt() == 0) {
                    binding.textErrorOne.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorOne.textError.visibility = View.GONE
                }
                if (binding.professionalismScore.rating.toInt() == 0) {
                    binding.textErrorTwo.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorTwo.textError.visibility = View.GONE
                }
                if (binding.courtesyScore.rating.toInt() == 0) {
                    binding.textErrorThree.textError.visibility = View.VISIBLE
                } else {
                    binding.textErrorThree.textError.visibility = View.GONE
                }
                if (binding.employeeFeedback.text.toString().isEmpty()) {
                    Log.d("employeeFeedback", binding.employeeFeedback.text.toString())
                    binding.EmployeeFeedback.error = " "
                } else {
                    binding.EmployeeFeedback.error = null
                }
                if (binding.companyFeedback.text.toString().isEmpty()) {
                    Log.d("companyFeedback", binding.companyFeedback.text.toString())
                    binding.CompanyFeedback.error = " "
                } else {
                    binding.CompanyFeedback.error = null
                }
                if (binding.helpFeedback.text.toString().isEmpty()) {
                    Log.d("helpFeedback", binding.helpFeedback.text.toString())
                    binding.HelpFeedback.error = " "

                } else {
                    binding.HelpFeedback.error = null
                }
            }
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_leaveFeedbackFragment_to_moreFragment)
        }

        viewModelServer.sendRatingByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == "OK") {
                goMainDialog.show(requireActivity().supportFragmentManager, "main")
            } else {
                Snackbar.make(
                    binding.linearRoot,
                    "нельзя отправить отзыв ранее чем через 5 дней",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_leaveFeedbackFragment_to_moreFragment)

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)


    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}