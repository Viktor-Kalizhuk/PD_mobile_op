package com.example.antikolektor.More.ContactWithSpecialist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListHist
import com.example.antikolektor.DataListUserPanel
import com.example.antikolektor.Payment.PaimentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactWhithSpecialistViewModel:ViewModel() {


    private val repository = ContactWhithSpecialistRepository()

    private val _feedBack = MutableLiveData<String?>()
    val feedBack: LiveData<String?> = _feedBack

    private val _userPanel = MutableLiveData<DataListUserPanel?>()
    val userPanel: LiveData<DataListUserPanel?> = _userPanel


    fun getFeedBack(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getFeedback(token)

            _feedBack.postValue(response)
        }
    }

    fun getUserPanel(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserPanel(token)

            _userPanel.postValue(response)
        }
    }
}