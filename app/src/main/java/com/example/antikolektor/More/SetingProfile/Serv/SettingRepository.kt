package com.example.antikolektor.More.SetingProfile.Serv

import com.example.antikolektor.Server.Network

class SettingRepository {

    suspend fun addFurtherPhone(phone:String,token: String): String? {
        val request = Network.apiClient.addFurtherPhone(phone,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun confirmFurtherPhone(phone:String,code:String,token: String): String? {
        val request = Network.apiClient.confirmFurtherPhone(phone,code,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

}