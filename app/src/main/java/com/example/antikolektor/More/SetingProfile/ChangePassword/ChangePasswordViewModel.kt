package com.example.antikolektor.More.SetingProfile.ChangePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataProfile
import com.example.antikolektor.More.SetingProfile.SettingProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePasswordViewModel:ViewModel() {

    private val repository = ChangePasswordRepository()

    private val _changeCodeByLiveData = MutableLiveData<String?>()
    val changeCodeByLiveData: LiveData<String?> = _changeCodeByLiveData

    fun changeCode(code:String,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.changeCode(code,token)

            _changeCodeByLiveData.postValue(response)
        }
    }
}

