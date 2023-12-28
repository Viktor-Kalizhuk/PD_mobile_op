package com.example.antikolektor.Documents.MyDocumentsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataFile
import com.example.antikolektor.Documents.MyDocumentRepository
import com.example.antikolektor.MyDocumentDataStruct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PersonalDataViewModel:ViewModel() {
    private val repository = MyDocumentRepository()

    private val _documentByLiveData = MutableLiveData<MyDocumentDataStruct?>()
    val documentByLiveData: LiveData<MyDocumentDataStruct?> = _documentByLiveData

    private val _deleteByLiveData = MutableLiveData<String?>()
    val deleteByLiveData: LiveData<String?> = _deleteByLiveData

    private val _fileByLiveData = MutableLiveData<List<DataFile>?>()
    val fileByLiveData: LiveData<List<DataFile>?> = _fileByLiveData

    fun getClientFile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getClientFile(token)

            _documentByLiveData.postValue(response)
        }
    }

    fun deleteFile(id:Int,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteFile(id,token)

            _deleteByLiveData.postValue(response)
        }
    }

    fun addFile(file_type_id:Int,file:List<MultipartBody.Part>,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addFile(file_type_id,file,token)

            _fileByLiveData.postValue(response)
        }
    }

}