package com.example.antikolektor.Questionnaire

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.antikolektor.*
import com.example.antikolektor.Adapter.AdapterPhoto
import com.example.antikolektor.Documents.MyDocumentsScreen.AddPhotoFragment
import com.example.antikolektor.databinding.FragmentAddLenderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream


class AddLenderFragment : Fragment() {
    private val listFile = arrayListOf<MultipartBody.Part>()
    private var selectedSort: String? = null
    private val adapterPhoto = AdapterPhoto { callback1(it) }
    private val photoBottomSheet = AddPhotoFragment()
    private var listDocuments: String? = null
    private val dialogLoader = DeilogLoaderFragment()
    private val args: AddLenderFragmentArgs by navArgs()
    lateinit var binding: FragmentAddLenderBinding
    lateinit var dataStore: DataStore<Preferences>
    private var poz: Int? = null

    var list = arrayListOf<DataDoc>()
    private var creditId: Int? = null
    val viewModelServer: AddLendersViewModel by lazy {
        ViewModelProvider(this)[AddLendersViewModel::class.java]
    }

    fun callback1(it: DocumentsStrPoz) {
        when(it.bitmap){
            null->{
                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.deleteFileCredit(it.id!!.toInt(), "Bearer $value")
                }
                poz = it.pos
            }
            else->{
                adapterPhoto.removeItem(it.pos)
            //    list.removeAt(it.pos)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddLenderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStore = requireContext().createDataStore(name = "settings")
        init()
        binding.autoCompleteTextView.addTextChangedListener(watcherAddres())
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "request_key", requireActivity()
        ) { _, bundle ->
            selectedSort = bundle.getString("key")
            Toast.makeText(requireContext(), selectedSort, Toast.LENGTH_LONG).show()
            if (selectedSort == "addG") {
                addGalery()
            } else {
                makePhoto()
            }
        }

        binding.ButtonSave.setOnClickListener {
            if (args.position == "create") {
                if (binding.autoCompleteTextView.text.toString().isEmpty()) {
                    binding.AutoCompleteTextView.error = " "
                    binding.nestedScrollView.smoothScrollTo(0, 0)
                } else {
                    dialogLoader.show(requireActivity().supportFragmentManager, "load")
                    lifecycleScope.launch(Dispatchers.IO) {
                        val value = readToken("qwe")
                        viewModelServer.addCredit("Bearer $value")
                    }
                }
            } else {
                if (args.position == "edit") {
                    if (binding.autoCompleteTextView.text.toString().isEmpty()) {
                        binding.AutoCompleteTextView.error = " "
                        binding.nestedScrollView.smoothScrollTo(0, 0)
                    } else {
                        val editTextPay: String?
                        val editTextSum: String?
                        if (binding.EditTextPay.text.toString().isEmpty()) {
                            editTextPay = " "
                        } else {
                            editTextPay = binding.EditTextPay.text.toString()
                        }
                        if (binding.EditTextSum.text.toString().isEmpty()) {
                            editTextSum = " "
                        } else {
                            editTextSum = binding.EditTextSum.text.toString()
                        }
                        dialogLoader.show(requireActivity().supportFragmentManager, "load")
                        lifecycleScope.launch(Dispatchers.IO) {
                            val value = readToken("qwe")
//                            Log.d( "file id",args.files.id.toString())
//                            Log.d( "autoCompleteTextView",binding.autoCompleteTextView.text.toString())
//                            Log.d( " id","0")
//                            Log.d( "EditTextSum",EditTextSum!!)
//                            Log.d( "EditTextPay",EditTextPay!!)
//                            Log.d( "listDocuments",listDocuments!!)
//                            Log.d( "token","Bearer $value")

                            viewModelServer.updateCredit(
                                args.files.id,
                                binding.autoCompleteTextView.text.toString(),
                                0,
                                editTextSum,
                                editTextPay,
                                listDocuments!!,
                                "Bearer $value"
                            )
                        }
                    }
                }
            }
        }

        when (args.position) {
            "create" -> {
            }
            "edit" -> {
                data()
            }
        }
        binding.ButtonAddFile.setOnClickListener {
            photoBottomSheet.setStyle(
                BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetTheme
            )
            photoBottomSheet.show(requireActivity().supportFragmentManager, "qwe")
        }



        binding.ButtonDelete.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.deleteCredit(args.files.id, "Bearer $value")
            }

        }
        viewModelServer.deleteFileCreditTitleByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == "OK") {
                adapterPhoto.removeItem(poz!!)
            }
        }

        viewModelServer.fileByLiveData.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                dialogLoader.dialog?.dismiss()
                findNavController().navigate(R.id.action_addLenderFragment_to_questionnaireFragment)
            }
        }
        viewModelServer.updateByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == "OK") {
                if (listFile.isEmpty()) {
                    dialogLoader.dialog?.dismiss()
                    findNavController().navigate(R.id.action_addLenderFragment_to_questionnaireFragment)

                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val value = readToken("qwe")
                        viewModelServer.addFile(1, args.files.id, listFile, "Bearer $value")
                    }
                }
            }
        }

        viewModelServer.deleteCreditTitleByLiveData.observe(viewLifecycleOwner) { response ->
            if (response == "OK") {
                findNavController().navigate(R.id.action_addLenderFragment_to_questionnaireFragment)

            }
        }
        viewModelServer.addCreditByLiveData.observe(viewLifecycleOwner) { response ->
            if (response?.id != null) {
                creditId = response.id
                val editTextPay: String?
                val editTextSum: String?
                if (binding.EditTextPay.text.toString().isEmpty()) {
                    editTextPay = " "
                } else {
                    editTextPay = binding.EditTextPay.text.toString()
                }
                if (binding.EditTextSum.text.toString().isEmpty()) {
                    editTextSum = " "
                } else {
                    editTextSum = binding.EditTextSum.text.toString()
                }

                lifecycleScope.launch(Dispatchers.IO) {
                    val value = readToken("qwe")
                    viewModelServer.updateCredit(
                        response.id,
                        binding.autoCompleteTextView.text.toString(),
                        0,
                        editTextSum,
                        editTextPay,
                        listDocuments!!,
                        "Bearer $value"
                    )
                }
            }
        }
        binding.Check1.setOnClickListener {
            addDocumentsYouHave(binding.Check1, binding.Text1)
        }
        binding.Check2.setOnClickListener {
            addDocumentsYouHave(binding.Check2, binding.Text2)
        }
        binding.Check3.setOnClickListener {
            addDocumentsYouHave(binding.Check3, binding.Text3)
        }
        binding.Check4.setOnClickListener {
            addDocumentsYouHave(binding.Check4, binding.Text4)
        }
        viewModelServer.getCreditTitleByLiveData.observe(viewLifecycleOwner) { response ->
            val list = arrayListOf<String>()
            response!!.data.forEach {
                list.add(it.title)
            }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)

            binding.autoCompleteTextView.setAdapter(arrayAdapter)
        }

        binding.toolbar2.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addLenderFragment_to_questionnaireFragment)
        }

    }

    private fun pastText(edText: EditText, args: String) {
        if (args.isNotEmpty()) {
            edText.setText(args)
        }
    }

    fun data() {
        pastText(binding.autoCompleteTextView, args.files.title.toString())
        pastText(binding.EditTextSum, args.files.balance_owed.toString())
        pastText(binding.EditTextPay, args.files.monthly_payment.toString())

        if (args.files.files.isNotEmpty()) {
            val arrayListFiles = arrayListOf<DocumentsStrr>()
            args.files.files.forEach() {
                arrayListFiles.add(
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

            }
            adapterPhoto.addRepoz(arrayListFiles)
        }
        listDocuments = args.files.documents_you_have
        if (args.files.documents_you_have!!.isNotEmpty()) {
            val list = args.files.documents_you_have.toString().split(";")
            list.forEach {
                when (it.lowercase()) {
                    binding.Text1.text.toString() -> {
                        binding.Check1.isChecked = true
                    }
                    binding.Text2.text.toString() -> {
                        binding.Check2.isChecked = true
                    }
                    binding.Text3.text.toString() -> {
                        binding.Check3.isChecked = true
                    }
                    binding.Text4.text.toString() -> {
                        binding.Check4.isChecked = true
                    }
                }
            }
        }
    }

    private fun watcherAddres(): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            lifecycleScope.launch(Dispatchers.IO) {
                val value = readToken("qwe")
                viewModelServer.getCreditTitle(s.toString(), "Bearer $value")
            }
        }
    }

    private fun addDocumentsYouHave(checkBox: CheckBox, textView: TextView) {
        if (checkBox.isChecked == true) {
            val text = textView.text
            listDocuments += ";${text}"
            Log.d("deb", listDocuments.toString())

        } else {
            val text = textView.text
            listDocuments = listDocuments.toString().replace(";${text}", "")
            Log.d("deb", listDocuments.toString())
        }
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
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
            val arrayListFiles = arrayListOf<DocumentsStrr>()
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

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
                listFile.add(body)
                arrayListFiles.add(
                    DocumentsStrr(
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        "",
                        "",
                        bitmap
                    )
                )
                adapterPhoto.addRepoz(arrayListFiles)

            }
        }


    private val galleryForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

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


            }
        }

    private fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapterPhoto
        }
    }
}