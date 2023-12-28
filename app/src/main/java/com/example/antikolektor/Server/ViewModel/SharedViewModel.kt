package com.example.antikolektor.Server.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.RoomDatabase.UserDatabase
import com.example.antikolektor.Server.Code
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class SharedViewModel : ViewModel() {
    private val repository = SharedRepository()

    private val _tokenByLiveData = MutableLiveData<String?>()
    val tokenByIdLiveData: LiveData<String?> = _tokenByLiveData

    private val _codeByLiveData = MutableLiveData<Code?>()
    val codeByIdLiveData: LiveData<Code?> = _codeByLiveData

    private val _regenerateCodeByLiveData = MutableLiveData<String?>()
    val regenerateCodeByIdLiveData: LiveData<String?> = _regenerateCodeByLiveData

    fun refreshToken(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getToken(login, password)

            _tokenByLiveData.postValue(response)
        }
    }

    fun refreshCode(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCode(login)

            _codeByLiveData.postValue(response)
        }
    }

    fun regenerateCode(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRedgenerateCode(login)

            _regenerateCodeByLiveData.postValue(response)
        }
    }
}