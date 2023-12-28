package com.example.antikolektor.Documents.ShareDocuments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataFile
import com.example.antikolektor.Documents.MyDocumentRepository
import com.example.antikolektor.MyDocumentDataStruct
import com.example.antikolektor.SharedDataDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class SharedDocumentsViewModel: ViewModel() {

    private val repository = MyDocumentRepository()

    private val _fileByLiveData = MutableLiveData<List<DataFile>?>()
    val fileByLiveData: LiveData<List<DataFile>?> = _fileByLiveData

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

    fun deleteFile(id:Int,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteFile(id,token)

            _deleteByLiveData.postValue(response)
        }
    }

    fun addFile(file_type_id:Int, file:List<MultipartBody.Part>, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addFile(file_type_id,file,token)

            _fileByLiveData.postValue(response)
        }
    }
}