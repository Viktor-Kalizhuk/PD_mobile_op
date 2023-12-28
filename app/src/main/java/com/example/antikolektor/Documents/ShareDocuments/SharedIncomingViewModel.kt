package com.example.antikolektor.Documents.ShareDocuments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataFile
import com.example.antikolektor.Documents.MyDocumentRepository
import com.example.antikolektor.SharedDataDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class SharedIncomingViewModel: ViewModel() {


    private val repository = MyDocumentRepository()

    private val _fileByLiveData = MutableLiveData<DataFile?>()
    val fileByLiveData: LiveData<DataFile?> = _fileByLiveData

    private val _deleteByLiveData = MutableLiveData<String?>()
    val deleteByLiveData: LiveData<String?> = _deleteByLiveData

    private val _sharedDocumentByLiveData = MutableLiveData<List<SharedDataDocument>?>()
    val sharedDocumentByLiveData: LiveData<List<SharedDataDocument>?> = _sharedDocumentByLiveData

    fun getClientExchangeFiles(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getClientExchangeFiles(token)

            _sharedDocumentByLiveData.postValue(response)
        }
    }


}