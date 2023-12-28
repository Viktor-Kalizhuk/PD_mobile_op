package com.example.antikolektor.Questionnaire

import com.example.antikolektor.DataProfile
import com.example.antikolektor.Server.Network

class StepThreeRepository {
    suspend fun getProfile(token:String): DataProfile? {
        val request = Network.apiClient.getProfile(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}