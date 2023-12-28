package com.example.antikolektor.Documents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.antikolektor.MyDocumentDataStruct
import com.example.antikolektor.Server.ViewModel.SharedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyDocumentViewModel:ViewModel() {

    private val repository = MyDocumentRepository()

    private val _documentByLiveData = MutableLiveData<MyDocumentDataStruct?>()
    val documentByLiveData: LiveData<MyDocumentDataStruct?> = _documentByLiveData

    fun getClientFile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getClientFile(token)

            _documentByLiveData.postValue(response)
        }
    }
}