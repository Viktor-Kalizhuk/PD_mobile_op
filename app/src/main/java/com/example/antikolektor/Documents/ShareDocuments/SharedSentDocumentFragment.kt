package com.example.antikolektor.Documents.ShareDocuments

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.Adapter.AdapterPhoto
import com.example.antikolektor.DocumentsStr
import com.example.antikolektor.DocumentsStrPoz
import com.example.antikolektor.DocumentsStrr
import com.example.antikolektor.databinding.FragmentSharedSentDocumentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.*


class SharedSentDocumentFragment : Fragment() {
    var selectedSort: String? = null
    private val GALLERY_REQUEST = 2
    private val REQUEST_TAKE_PHOTO = 1
    private val adapter = AdapterPhoto { callback1(it) }
    lateinit var binding: FragmentSharedSentDocumentBinding
    lateinit var dataStore: DataStore<Preferences>
    var list = arrayListOf<DocumentsStrr>()
    val viewModelServer: SharedDocumentsViewModel by lazy {
        ViewModelProvider(this)[SharedDocumentsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSharedSentDocumentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(requireContext(), "pause", Toast.LENGTH_SHORT).show()
        adapter.removeItemAll()
    }

    override fun onResume() {
        super.onResume()
        adapter.removeItemAll()
        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getClientExchangeFiles("Bearer $value")
        }

    }

    fun callback1(it: DocumentsStrPoz) {
        if (it.sender_id == it.user_id) {

            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.deleteFile(it.id!!, "Bearer $value")
            }
            adapter.removeItem(it.pos)
        } else {
            val uri = Uri.parse("https://lk.pravoe-delo.su/storage/" + it.path)
            val request = DownloadManager.Request(uri)
            request.setVisibleInDownloadsUi(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDescription("Download...")
            request.setAllowedOverMetered(true)

            val dm = requireContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelServer.sharedDocumentByLiveData.observe(viewLifecycleOwner) { response ->
            list.clear()
            if (response != null) {
                response.forEach {
                    if (it.sender_id == it.user_id) {
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
                                it.updated_at,null
                            )
                        )
                    }
                }
                adapter.removeItemAll()
                adapter.addRepoz(list)

            }
        }

        dataStore = requireContext().createDataStore(name = "settings")
        init()
        adapter.removeItemAll()

        viewModelServer.fileByLiveData.observe(viewLifecycleOwner) {
            list.clear()
            if (it!!.isNotEmpty()) {
                it.forEach {
                    list.add(
                        DocumentsStrr(
                            it.id,
                            it.name,
                            " ",
                            it.type,
                            it.size,
                            it.path,
                            0,
                            it.user_id,
                            it.file_type_id,
                            0,
                            0,
                            it.sender_id,
                            it.created_at,
                            it.updated_at,
                            null
                        ))
                }
                adapter.addRepoz(list)
            }
        }

        requireActivity().supportFragmentManager
            .setFragmentResultListener("request", requireActivity()) { _, bundle ->
                selectedSort = bundle.getString("key")
                Toast.makeText(requireContext(), selectedSort, Toast.LENGTH_LONG).show()
                if (selectedSort == "addGG") {
                    addGalery()
                } else {
                    makePhoto()
                }
            }
    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter
        }
    }

    fun addGalery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
    }

    fun makePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == AppCompatActivity.RESULT_OK) {
                val listM = arrayListOf<MultipartBody.Part>()
                val uri = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(
                    context?.contentResolver, Uri.parse(uri.toString())
                )
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                val body = MultipartBody.Part.createFormData(
                    "filelist",
                    "photo_",
                    byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
                )
                listM.add(body)
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.addFile(2, listM, "Bearer $value")
                }
            }

            REQUEST_TAKE_PHOTO -> {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    val listfile = arrayListOf<MultipartBody.Part>()
                    val thumbnailBitmap = data?.extras?.get("data") as Bitmap

                    val stream = ByteArrayOutputStream()
                    thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val byteArray = stream.toByteArray()

                    val body = MultipartBody.Part.createFormData(
                        "filelist",
                        "photo_",
                        byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
                    )
                    listfile.add(body)
                    lifecycleScope.launch(Dispatchers.IO) {
                        val value = readToken("qwe")
                        viewModelServer.addFile(2, listfile, "Bearer $value")
                    }
                }
            }
        }

    }


    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }
}