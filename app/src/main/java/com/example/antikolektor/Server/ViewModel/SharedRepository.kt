package com.example.antikolektor.Server.ViewModel

import com.example.antikolektor.Server.Code
import com.example.antikolektor.Server.Data
import com.example.antikolektor.Server.DataByResponse
import com.example.antikolektor.Server.Network
import okhttp3.ResponseBody


class SharedRepository {
    suspend fun getToken(login: String, password: String): String? {
        val request = Network.apiClient.getToken(login, password)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun getCode(login: String): Code? {
        val request = Network.apiClient.getCode(login)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun getRedgenerateCode(login: String): String? {
        val request = Network.apiClient.getRegenerateCode(login)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun getBasePhones(token: String): DataByResponse? {
        val request = Network.apiClient.getBasePhones(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun getUserBasePhones(token: String): DataByResponse? {
        val request = Network.apiClient.getUserBasePhones(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun postUserBasePhones(phone:String,comment:String,type:String,subtype:String,token:String): String? {
        val request = Network.apiClient.postUserBasePhones(phone.toString(),comment.toString(),type.toString(),subtype.toString(),token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun deleteUserPhones(id:Int,token:String): String? {
        val request = Network.apiClient.deleteUserPhones(id,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}