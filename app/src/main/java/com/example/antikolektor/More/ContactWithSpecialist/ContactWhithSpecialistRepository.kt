package com.example.antikolektor.More.ContactWithSpecialist

import com.example.antikolektor.DataListHist
import com.example.antikolektor.DataListUserPanel
import com.example.antikolektor.Server.Network

class ContactWhithSpecialistRepository {

    suspend fun getFeedback(token: String): String? {
        val request = Network.apiClient.getFeedback(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun getUserPanel(token: String): DataListUserPanel? {
        val request = Network.apiClient.getUserPanel(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}