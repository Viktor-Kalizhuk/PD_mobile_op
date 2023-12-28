package com.example.antikolektor.Documents.MyDocumentsScreen


import android.app.Activity
import android.app.DownloadManager


import android.content.Context
import android.content.Intent

import android.graphics.Bitmap

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.*
import com.example.antikolektor.Adapter.*

import com.example.antikolektor.databinding.FragmentPersonalDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream


class PersonalDataFragment : Fragment() {
    private val args: PersonalDataFragmentArgs by navArgs()
    lateinit var dataStore: DataStore<Preferences>


    val viewModelServer: PersonalDataViewModel by lazy {
        ViewModelProvider(this)[PersonalDataViewModel::class.java]
    }
    var id: Int? = null
    private var selectedSort: String? = null
    lateinit var binding: FragmentPersonalDataBinding
    private val photoBottomSheet = AddPhotoFragment()
    private val adapterPersonal = AdapterPersonalData { callback1(it) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalDataBinding.inflate(layoutInflater)
        return binding.root
    }


    fun callback1(it: DocumentsStrPoz) {
        when (it.action) {
            false -> {
                if (it.sender_id == it.user_id) {
                    id = it.id
                    lifecycleScope.launch(Dispatchers.IO) {
                        val value = readToken("qwe")
                        viewModelServer.deleteFile(it.id!!.toInt(), "Bearer $value")
//                        viewModelServer.getClientFile("Bearer $value")
                    }
                } else {
                    val uri = Uri.parse("https://lk.pravoe-delo.su/storage/" + it.path)
                    val request = DownloadManager.Request(uri)
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    request.setDescription("Download...")
                    request.setAllowedOverMetered(true)

                    val dm =
                        requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    dm.enqueue(request)
                }
            }
            true -> {
                id = it.id
                photoBottomSheet.setStyle(
                    BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme
                )
                photoBottomSheet.show(requireActivity().supportFragmentManager, "qwe")
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        binding.TvTitle.text = args.title
        init()
        adapterPersonal.removeAllItem()

        lifecycleScope.launch(Dispatchers.IO) {
            val value = readToken("qwe")
            viewModelServer.getClientFile("Bearer $value")
        }
        viewModelServer.documentByLiveData.observe(viewLifecycleOwner) { response ->
            adapterPersonal.removeAllItem()
            if (response!!.structure.isEmpty()) {
                //
            } else {
                response.structure.forEach { str ->
                    if (str.category == args.title) {
                        val list = arrayListOf<DocumentsStr>()
                        val listDoc = arrayListOf<DataDoc>()
                        val listData = arrayListOf<MyDocumentDataStruct>()
                        list.clear()
                        listDoc.clear()
                        listData.clear()
                        listDoc.add(str)
                        response.documents.forEach {
                            if (it.file_type_id == str.id) {
                                list.add(it)
                            }
                        }
                        listData.add(
                            MyDocumentDataStruct(listDoc, list)
                        )
                        adapterPersonal.addRepoz(listData)
                    }

                }
            }
        }

        viewModelServer.fileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.getClientFile("Bearer $value")
                }


            }

        }

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_personalDataFragment_to_myDocumentsFragment)
        }


        requireActivity().supportFragmentManager.setFragmentResultListener(
            "request_key",
            requireActivity()
        ) { _, bundle ->
            selectedSort = bundle.getString("key")
            Toast.makeText(requireContext(), selectedSort, Toast.LENGTH_LONG).show()
            if (selectedSort == "addG") {
                addGalery()
            } else {
                makePhoto()
            }
        }

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_personalDataFragment_to_myDocumentsFragment)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_personalDataFragment_to_myDocumentsFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }


    private fun addGalery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        takePhotoForResult.launch(photoPickerIntent)
    }

    private fun makePhoto() {
        galleryForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }


    private val takePhotoForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val listM = arrayListOf<MultipartBody.Part>()
                val uri = data?.data

                val bitmap = MediaStore.Images.Media.getBitmap(
                    context?.contentResolver, Uri.parse(uri.toString())
                )


//                val source = ImageDecoder.createSource(requireContext().contentResolver , Uri.parse(uri.toString() ))


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
                    viewModelServer.addFile(id!!, listM, "Bearer $value")
                }

            }
        }


    private val galleryForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val listFile = arrayListOf<MultipartBody.Part>()
                val thumbnailBitmap = data?.extras?.get("data") as Bitmap

                val stream = ByteArrayOutputStream()
                thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()

                val body = MultipartBody.Part.createFormData(
                    "filelist",
                    "photo_",
                    byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
                )
                listFile.add(body)
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.addFile(id!!, listFile, "Bearer $value")
                }

            }
        }


    private fun init() {
        binding.apply {
            RcView.layoutManager = LinearLayoutManager(requireContext())
            RcView.adapter = adapterPersonal
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
}