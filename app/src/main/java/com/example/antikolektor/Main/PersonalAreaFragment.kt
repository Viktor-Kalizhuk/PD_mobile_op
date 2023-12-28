package com.example.antikolektor.Main


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.AdapterStages
import com.example.antikolektor.DataStages
import com.example.antikolektor.Documents.DocumentsFragment
import com.example.antikolektor.Payment.PaymentFragment
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentPersonalAreaBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class PersonalAreaFragment : Fragment() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var binding: FragmentPersonalAreaBinding
    private val paymentBottomSheet = PaymentFragment()
    private val documentBottomSheet = DocumentsFragment()
    private val payBottomShit = GoPayBottomshitFragment()
    val adapter = AdapterStages { callback(it) }
    lateinit var dataStore: DataStore<Preferences>
    val viewModelServer: StagesViewModel by lazy {
        ViewModelProvider(this)[StagesViewModel::class.java]
    }

    private fun callback(it: DataStages) {
        if (it.active == "F") {
            payBottomShit.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,
                R.style.BottomSheetTheme
            )
            payBottomShit.show(requireActivity().supportFragmentManager, "Tage")
        } else {
            findNavController().navigate(PersonalAreaFragmentDirections.actionPersonalAreaFragmentToDetailsStagesFragment(it.stage_id.toString()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalAreaBinding.inflate(layoutInflater)
        return (binding.root)
    }

    fun init() {
        with(binding) {
            RcViewStages.layoutManager = LinearLayoutManager(requireContext())
            RcViewStages.adapter = adapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        dataStore = requireContext().createDataStore(name = "settings")
        init()
        binding.noQuest.AddQuest.setOnClickListener {
            findNavController().navigate(R.id.action_personalAreaFragment_to_questionnaireFragment)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getStages("Bearer $value")
            viewModelServer.getProfile("Bearer $value")
        }

        viewModelServer.stagesByLiveData.observe(viewLifecycleOwner) { response ->
            if (response?.data.isNullOrEmpty()) {
                binding.noQuest.LinearNoQuest.visibility = View.VISIBLE
            } else {
                val list = arrayListOf<DataStages>()
                list.clear()
                when (binding.tablayout.selectedTabPosition) {
                    1 -> {
                        response!!.data.forEach {
                            if (it.active == "N") {
                                list.add(it)
                            }
                        }
                    }
                    2 -> {
                        response!!.data.forEach {
                            if (it.active == "Y") {
                                list.add(it)
                            }
                        }
                    }
                    3 -> {
                        response!!.data.forEach {
                            if (it.active == "F") {
                                list.add(it)
                            }
                        }
                    }
                    else -> {
                        list.addAll(response!!.data)
                    }
                }
                adapter.addRepoz(list)
            }
        }

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.getStages("Bearer ${value!!}")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewModelServer.feedbacByLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    "OK" -> {
                        val snackbar = Snackbar.make(
                            binding.ConsLa,
                            "Специалист в ближайшее время с вами свяжется",
                            Snackbar.LENGTH_INDEFINITE
                        )

                        snackbar.setActionTextColor(
                            requireActivity().getColor(
                                R.color.error
                            )
                        )
                        snackbar.setAction(
                            "Хорошо"
                        ) {
                            snackbar.dismiss()
                        }
                        snackbar.show()
                    }
                }
            }
        }

        viewModelServer.profileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                if (response.first_name != null) {
                    binding.TvName.text =
                        binding.TvName.text.toString() + " ${response.first_name.toString()}"
                } else {
                    if (response.phone.first().phone != null) {
                        binding.TvName.text =
                            binding.TvName.text.toString() + " ${response.phone.first().phone.toString()}"
                    }
                }
            } else {
                Toast.makeText(requireContext(), "запрос не выполнен", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar2.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_icon, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.call -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val value = readToken("qwe")
                            viewModelServer.getFeedback("Bearer ${value!!}")
                        }
                        true
                    }
                    R.id.coll -> {
                        findNavController().navigate(PersonalAreaFragmentDirections.actionPersonalAreaFragmentToNotificationFragment())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)



        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.mainItem -> {
                }
                R.id.paymentItem -> {
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Переход на экран оплаты")
                    firebaseAnalytics.logEvent("main_pay", bundle)
                    paymentBottomSheet.setStyle(
                        BottomSheetDialogFragment.STYLE_NORMAL,
                        R.style.BottomSheetTheme
                    )
                    paymentBottomSheet.show(requireActivity().supportFragmentManager, "Tag")
                }
                R.id.questionnaireItem -> {
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Переход на экран анкеты")
                    firebaseAnalytics.logEvent("main_questionnaire", bundle)
                    findNavController().navigate(R.id.action_personalAreaFragment_to_questionnaireFragment)
                }
                R.id.documentationItem -> {
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Переход на экран документы")
                    firebaseAnalytics.logEvent("main_documents", bundle)
                    documentBottomSheet.setStyle(
                        BottomSheetDialogFragment.STYLE_NORMAL,
                        R.style.BottomSheetTheme
                    )
                    documentBottomSheet.show(requireActivity().supportFragmentManager, "tag")

                }
                R.id.moreItem -> {
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Переход на экран еще")
                    firebaseAnalytics.logEvent("main_more", bundle)
                    findNavController().navigate(R.id.action_personalAreaFragment_to_moreFragment)
                }
            }
            true
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
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