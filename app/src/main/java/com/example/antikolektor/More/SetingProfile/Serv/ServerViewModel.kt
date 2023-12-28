package com.example.antikolektor.More.SetingProfile.Serv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServerViewModel:ViewModel() {
    private val repository = SettingRepository()

    private val _addFurtherPhone = MutableLiveData<String?>()
    val addFutherPhone: LiveData<String?> = _addFurtherPhone


    fun addFutherPhone(phone:String,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addFurtherPhone(phone,token)

            _addFurtherPhone.postValue(response)
        }
    }
}