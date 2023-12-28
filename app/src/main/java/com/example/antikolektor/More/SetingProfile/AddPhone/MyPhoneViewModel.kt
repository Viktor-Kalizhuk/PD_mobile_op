package com.example.antikolektor.More.SetingProfile.AddPhone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataProfile
import com.example.antikolektor.More.SetingProfile.SettingProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPhoneViewModel: ViewModel() {
    private val repository = SettingProfileRepository()

    private val _profileByLiveData = MutableLiveData<DataProfile?>()
    val profileByLiveData: LiveData<DataProfile?> = _profileByLiveData

    private val _deletePhoneByLiveData = MutableLiveData<String?>()
    val deletePhoneByLiveData: LiveData<String?> = _deletePhoneByLiveData

    fun getProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProfile(token)

            _profileByLiveData.postValue(response)
        }
    }
 fun deleteFurtherPhone(phone:String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteFurtherPhone(phone,token)

            _deletePhoneByLiveData.postValue(response)
        }
    }

}