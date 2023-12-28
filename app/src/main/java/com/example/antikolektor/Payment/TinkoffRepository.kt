package com.example.antikolektor.Payment

import com.example.antikolektor.DataListHist
import com.example.antikolektor.IdRemittance
import com.example.antikolektor.Server.Network

class TinkoffRepository {

    suspend fun makeRemittance(amount:String,token: String): IdRemittance? {
        val request = Network.apiClient.makeRemittance( amount ,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}