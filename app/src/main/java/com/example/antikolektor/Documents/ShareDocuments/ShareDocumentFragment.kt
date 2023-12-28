package com.example.antikolektor.Documents.ShareDocuments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.antikolektor.Adapter.AdapterDocuments
import com.example.antikolektor.Adapter.AdapterPhoto
import com.example.antikolektor.Documents.MyDocumentViewModel
import com.example.antikolektor.DocumentsStrPoz
import com.example.antikolektor.R
import com.example.antikolektor.databinding.FragmentShareDocumentBinding
import com.example.app_spisho_dolgi.VPAdapter.VpAdapterShare
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator


class ShareDocumentFragment : Fragment() {

    lateinit var dataStore: DataStore<Preferences>
    var list = arrayListOf<String>()
    val viewModelServer: SharedDocumentsViewModel by lazy {
        ViewModelProvider(this).get(SharedDocumentsViewModel::class.java)
    }
    val adapter = AdapterPhoto { callback1(it) }
    lateinit var binding: FragmentShareDocumentBinding
    val sharedAddDocumentFragment = SharedAddDocumentFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareDocumentBinding.inflate(layoutInflater)
        return binding.root
    }

    fun callback1(it: DocumentsStrPoz) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.viewpager.adapter = VpAdapterShare(requireActivity())

        binding.adddoc.setOnClickListener {
            sharedAddDocumentFragment.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL,
                R.style.BottomSheetTheme
            )
            sharedAddDocumentFragment.show(requireActivity().supportFragmentManager, "TAgg")
        }

        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Отправленные"
                }
                1 -> {
                    "Входящие"
                }
                else -> {
                    throw Resources.NotFoundException("Position Not Found")
                }
            }
        }.attach()


        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_shareDocumentFragment_to_personalAreaFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_shareDocumentFragment_to_personalAreaFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}