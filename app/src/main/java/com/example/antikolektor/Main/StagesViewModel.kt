package com.example.antikolektor.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListStages
import com.example.antikolektor.DataProfile
import com.example.antikolektor.DataStages
import com.example.antikolektor.Server.ViewModel.SharedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StagesViewModel:ViewModel() {
    private val repository = StagesRepository()

    private val _stagesByLiveData = MutableLiveData<DataListStages?>()
    val stagesByLiveData: LiveData<DataListStages?> = _stagesByLiveData

    private val _profileByLiveData = MutableLiveData<DataProfile?>()
    val profileByLiveData: LiveData<DataProfile?> = _profileByLiveData

    private val _feedbackByLiveData = MutableLiveData<String?>()
    val feedbacByLiveData: LiveData<String?> = _feedbackByLiveData


    fun getFeedback(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getFeedback(token)

            _feedbackByLiveData.postValue(response)
        }
    }


    fun getStages(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserStages(token)

            _stagesByLiveData.postValue(response)
        }
    }


    fun getProfile(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getProfile(token)

            _profileByLiveData.postValue(response)
        }
    }
}