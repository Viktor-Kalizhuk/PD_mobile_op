package com.example.antikolektor.Notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListNotif
import com.example.antikolektor.Server.DataByResponse
import com.example.antikolektor.Server.ViewModel.SharedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel:ViewModel() {
    private val repository = NtificationRepository()

    private val _getNotification = MutableLiveData<DataListNotif?>()
    val getNotification: LiveData<DataListNotif?> = _getNotification

    private val _readNotification = MutableLiveData<String?>()
    val readNptification: LiveData<String?> = _readNotification

    private val _readAllNotification = MutableLiveData<String?>()
    val readAllNotification: LiveData<String?> = _readAllNotification


    fun getNotif(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getNotifications(token)

            _getNotification.postValue(response)
        }
    }
    fun readNotif(id:String,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.readNotification(id,token)

            _readNotification.postValue(response)
        }
    }
    fun readAllNotification(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.readAllNotification(token)

            _readAllNotification.postValue(response)
        }
    }
}