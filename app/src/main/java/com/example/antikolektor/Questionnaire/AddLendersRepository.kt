package com.example.antikolektor.Questionnaire

import com.example.antikolektor.*
import com.example.antikolektor.Server.Network
import okhttp3.MultipartBody

class AddLendersRepository {
    suspend fun getCreditTitle(serch:String,token:String): DataSerch? {
        val request = Network.apiClient.getCreditTitle(serch,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun updateCredit(id:Int,title:String, bank_id:Int,amount_of_credit:String, monthly_paiment:String, documents_you_have:String,token:String): String? {
        val request = Network.apiClient.updateCredit(id,title, bank_id,amount_of_credit, monthly_paiment, documents_you_have,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun addCredit(token:String): IdCredit? {
        val request = Network.apiClient.addCredit(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun deleteCredit(id:Int,token:String): String? {
        val request = Network.apiClient.deleteCredit(id,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun addFileCredit(file_type_id:Int,credit_id:Int, file:List<MultipartBody.Part>, token: String): List<DataFile>? {
        val request = Network.apiClient.addFileCredit(file_type_id,credit_id,file,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
    suspend fun deleteFileCredit(id:Int,token:String): String? {
        val request = Network.apiClient.deleteFile(id,token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }
}