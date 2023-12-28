package com.example.antikolektor.Documents


import com.example.antikolektor.DataFile
import com.example.antikolektor.MyDocumentDataStruct
import com.example.antikolektor.Server.Network
import com.example.antikolektor.SharedDataDocument
import okhttp3.MultipartBody

class MyDocumentRepository {

    suspend fun getClientFile(token: String): MyDocumentDataStruct? {
        val request = Network.apiClient.getClientFiles(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun deleteFile(id:Int,token: String): String? {
        val request = Network.apiClient.deleteFile(id,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun addFile(file_type_id:Int,file:List<MultipartBody.Part>,token: String): List<DataFile>? {
        val request = Network.apiClient.addFile(file_type_id,file,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }


    suspend fun getClientExchangeFiles(token: String): List<SharedDataDocument>? {
        val request = Network.apiClient.getClientExchangeFiles(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

}