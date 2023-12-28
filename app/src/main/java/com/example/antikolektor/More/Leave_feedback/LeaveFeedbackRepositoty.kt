package com.example.antikolektor.More.Leave_feedback

import com.example.antikolektor.Server.Network
import retrofit2.http.Header
import retrofit2.http.Query

class LeaveFeedbackRepositoty {
    suspend fun sendRating(overall_score:Int, professionalism_score:Int, courtesy_score:Int, employee_feedback:String, company_feedback:String, help_feedback:String, improve_feedback:String, header: String): String? {
        val request = Network.apiClient.sendRating(overall_score, professionalism_score, courtesy_score, employee_feedback, company_feedback, help_feedback, improve_feedback, header)


        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}