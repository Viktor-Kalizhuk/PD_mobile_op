package com.example.antikolektor.Payment

import com.example.antikolektor.DataListHist
import com.example.antikolektor.Server.Network

class PaimentRepository {

    suspend fun getUserReittances(token: String): DataListHist? {
        val request = Network.apiClient.getUserReittances(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}