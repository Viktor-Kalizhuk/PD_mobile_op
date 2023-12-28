package com.example.antikolektor.Questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataProfile
import com.example.antikolektor.putProfile
import com.example.antikolektor.putProfileStepTwo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepTwoViewModel:ViewModel() {
    private val repository = StepTwoRepositiry()

    private val _profileByLiveData = MutableLiveData<DataProfile?>()
    val profileByLiveData: LiveData<DataProfile?> = _profileByLiveData

    private val _updateByLiveData = MutableLiveData<String?>()
    val updateByLiveData: LiveData<String?> = _updateByLiveData

    fun getProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProfile(token)

            _profileByLiveData.postValue(response)
        }
    }
    fun updateProfile(id: Int, userData: putProfileStepTwo, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateProfileStepTwo(id,userData,token)

            _updateByLiveData.postValue(response)
        }
    }
}