package com.example.antikolektor.Documents.ShareDocuments

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.AdapterPhoto
import com.example.antikolektor.DocumentsStr
import com.example.antikolektor.DocumentsStrPoz
import com.example.antikolektor.DocumentsStrr

import com.example.antikolektor.databinding.FragmentSharedIncomingDocumentBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SharedIncomingDocumentFragment : Fragment() {
    private val adapter = AdapterPhoto { callback1(it) }
    lateinit var dataStore: DataStore<Preferences>
    var list = arrayListOf<DocumentsStrr>()
    val viewModelServer: SharedDocumentsViewModel by lazy {
        ViewModelProvider(this)[SharedDocumentsViewModel::class.java]
    }
    lateinit var binding: FragmentSharedIncomingDocumentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSharedIncomingDocumentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = requireContext().createDataStore(name = "settings")
        init()
        viewModelServer.sharedDocumentByLiveData.observe(viewLifecycleOwner) { response ->
            list.clear()
            if (response != null) {
                response.forEach {
                    if(it.sender_id != it.user_id){
                    list.add(
                        DocumentsStrr(
                            it.id,
                            it.name,
                            it.original_name,
                            it.type,
                            it.size,
                            it.path,
                            it.sort,
                            it.user_id,
                            it.file_type_id,
                            it.stage_id,
                            it.credit_id,
                            it.sender_id,
                            it.created_at,
                            it.updated_at,
                            null
                        )
                    )
                }}
                adapter.removeItemAll()
                adapter.addRepoz(list)

            }
        }

    }
    override fun onResume() {
        super.onResume()
        adapter.removeItemAll()
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getClientExchangeFiles("Bearer $value")
        }

    }
    private fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter
        }
    }
    fun callback1(it:DocumentsStrPoz) {
        if (it.sender_id == it.user_id) {

            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.deleteFile(it.id!!, "Bearer $value")
            }
            adapter.removeItem(it.pos)
        } else {
            val uri = Uri.parse("https://lk.pravoe-delo.su/storage/" + it.path)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDescription("Download...")
            request.setAllowedOverMetered(true)

            val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }
    }
    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}