package com.example.antikolektor.Payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListHist
import com.example.antikolektor.Server.DataByResponse
import com.example.antikolektor.Server.ViewModel.SharedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaimentViewModel:ViewModel() {


    private val repository = PaimentRepository()

    private val _paymentHist = MutableLiveData<DataListHist?>()
    val paimentHist: LiveData<DataListHist?> = _paymentHist


    fun getHistPayment(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserReittances(token)

            _paymentHist.postValue(response)
        }
    }
}