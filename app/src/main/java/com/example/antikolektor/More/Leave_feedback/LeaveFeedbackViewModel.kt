package com.example.antikolektor.More.Leave_feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaveFeedbackViewModel:ViewModel() {
    private val repository = LeaveFeedbackRepositoty()

    private val _sendRating = MutableLiveData<String?>()
    val sendRatingByLiveData: LiveData<String?> = _sendRating


    fun sendRating(overall_score:Int, professionalism_score:Int, courtesy_score:Int, employee_feedback:String, company_feedback:String, help_feedback:String, improve_feedback:String, header: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.sendRating(overall_score, professionalism_score, courtesy_score, employee_feedback, company_feedback, help_feedback, improve_feedback, header)


            _sendRating.postValue(response)
        }
    }
}