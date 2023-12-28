package com.example.antikolektor.Main

import android.text.Html
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataListStages
import com.example.antikolektor.DataProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.util.Date

class DetailsStagesViewModel : ViewModel() {

    private val repository = StagesRepository()

    private val _detailsStagesByLiveData = MutableLiveData<String?>()
    val detailsStagesByLiveData: LiveData<String?> = _detailsStagesByLiveData

    fun getViewStages(id: Int, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getViewStages(id, token)
//            Log.d("response", response)
            _detailsStagesByLiveData.postValue(response)
        }
    }

}