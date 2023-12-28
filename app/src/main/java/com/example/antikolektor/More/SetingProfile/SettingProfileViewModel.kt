package com.example.antikolektor.More.SetingProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingProfileViewModel: ViewModel() {
    private val repository = SettingProfileRepository()

    private val _profileByLiveData = MutableLiveData<DataProfile?>()
    val profileByLiveData: LiveData<DataProfile?> = _profileByLiveData

    fun getProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProfile(token)

            _profileByLiveData.postValue(response)
        }
    }
}