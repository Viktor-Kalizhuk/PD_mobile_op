package com.example.antikolektor.More.SetingProfile.ChangePassword

import com.example.antikolektor.DataProfile
import com.example.antikolektor.Server.Network

class ChangePasswordRepository {

    suspend fun changeCode(code:String,token:String): String? {
        val request = Network.apiClient.changeCode(code,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}