package com.example.antikolektor.Main


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.text.htmlEncode
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs
import com.example.antikolektor.R

import com.example.antikolektor.databinding.FragmentDetailsStagesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class DetailsStagesFragment : Fragment() {
    private var myWebView: WebView? = null
    lateinit var binding: FragmentDetailsStagesBinding
    lateinit var dataStore: DataStore<Preferences>
    private val args:DetailsStagesFragmentArgs by navArgs()
    val viewModelServer: DetailsStagesViewModel by lazy {
        ViewModelProvider(this)[DetailsStagesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsStagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getViewStages(args.id.toInt(),"Bearer $value")
        }

        viewModelServer.detailsStagesByLiveData.observe(viewLifecycleOwner){response->
            Log.d("response", response!!)
//            binding.WebView.loadUrl("https://lk.pravoe-delo.su/api/v1/getViewStage/1")
                printWebView(response)
        }
        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailsStagesFragment_to_personalAreaFragment)
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun printWebView(html:String) {
        val webView = binding.WebView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
//                createWebPrintJob(view)
                myWebView = null
            }
        }

        webView.loadDataWithBaseURL(
            null, html,
            "text/HTML", "UTF-8", null
        )

        myWebView = webView
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}