package com.example.antikolektor.Questionnaire

import com.example.antikolektor.DataListDirectory
import com.example.antikolektor.DataProfile
import com.example.antikolektor.Server.Network
import com.example.antikolektor.Server.NetworkDirectory
import com.example.antikolektor.putProfile

class StepOnreRepository {
    suspend fun getProfile(token:String): DataProfile? {
        val request = Network.apiClient.getProfile(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun postDirectory(query:String,token:String): DataListDirectory? {
        val request = NetworkDirectory.apiClient.postDirectory(query ,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun updateProfile(id:Int,userData:putProfile,token:String): String? {
        val request = Network.apiClient.updateProfile(id,userData ,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}