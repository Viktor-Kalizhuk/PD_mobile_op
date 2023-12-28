package com.example.antikolektor.Payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListHist
import com.example.antikolektor.IdRemittance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAmount

class TinkoffViewModel:ViewModel() {

    private val repository = TinkoffRepository()

    private val _idRemittance = MutableLiveData<IdRemittance?>()
    val idRemittance: LiveData<IdRemittance?> = _idRemittance


    fun makeRemittance(amount: String,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.makeRemittance(amount,token)

            _idRemittance.postValue(response)
        }
    }
}