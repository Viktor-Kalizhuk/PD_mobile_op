package com.example.antikolektor.Questionnaire

import com.example.antikolektor.DataProfile
import com.example.antikolektor.Server.Network
import com.example.antikolektor.putProfile
import com.example.antikolektor.putProfileStepTwo

class StepTwoRepositiry {
    suspend fun getProfile(token:String): DataProfile? {
        val request = Network.apiClient.getProfile(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun updateProfileStepTwo(id:Int, userData: putProfileStepTwo, token:String): String? {
        val request = Network.apiClient.updateProfileStepTwo(id,userData ,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}