package com.example.antikolektor.More.SetingProfile.Serv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel:ViewModel() {
    private val repository=SettingRepository()


    private val _confirmFurtherPhone = MutableLiveData<String?>()
    val confirmFurtherPhone: LiveData<String?> = _confirmFurtherPhone


    fun confirmFurtherPhone(phone:String,code:String,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.confirmFurtherPhone(phone,code,token)

            _confirmFurtherPhone.postValue(response)
        }
    }

}