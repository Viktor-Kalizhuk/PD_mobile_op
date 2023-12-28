package com.example.antikolektor.Notification

import com.example.antikolektor.DataListNotif
import com.example.antikolektor.Server.Network

class NtificationRepository {

    suspend fun getNotifications(token: String): DataListNotif? {
        val request = Network.apiClient.getNotifications(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun readNotification(id:String,token: String): String? {
        val request = Network.apiClient.readNotification(id,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun readAllNotification(token: String): String? {
        val request = Network.apiClient.readAllNotification(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}