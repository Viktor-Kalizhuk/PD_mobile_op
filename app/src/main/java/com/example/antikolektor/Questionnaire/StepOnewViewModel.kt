package com.example.antikolektor.Questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListDirectory
import com.example.antikolektor.DataProfile
import com.example.antikolektor.putProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query

class StepOnewViewModel:ViewModel() {
    private val repository = StepOnreRepository()

    private val _profileByLiveData = MutableLiveData<DataProfile?>()
    val profileByLiveData: LiveData<DataProfile?> = _profileByLiveData

    private val _directoryByLiveData = MutableLiveData<DataListDirectory?>()
    val directoryByLiveData: LiveData<DataListDirectory?> = _directoryByLiveData

    private val _updateByLiveData = MutableLiveData<String?>()
    val updateByLiveData: LiveData<String?> = _updateByLiveData

    fun getProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProfile(token)

            _profileByLiveData.postValue(response)
        }
    }
    fun postDirectory(query: String,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postDirectory(query,token)

            _directoryByLiveData.postValue(response)
        }
    }
    fun updateProfile(id: Int,userData:putProfile,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateProfile(id,userData,token)

            _updateByLiveData.postValue(response)
        }
    }
}