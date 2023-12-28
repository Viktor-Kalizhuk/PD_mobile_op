package com.example.antikolektor.More.SetingProfile

import com.example.antikolektor.DataProfile
import com.example.antikolektor.Server.Network

class SettingProfileRepository {
    suspend fun getProfile(token:String): DataProfile? {
        val request = Network.apiClient.getProfile(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun deleteFurtherPhone(phone:String,token:String): String? {
        val request = Network.apiClient.deleteFurtherPhone(phone,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}