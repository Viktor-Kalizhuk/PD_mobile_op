package com.example.antikolektor.Main

import android.text.Html
import android.util.Log
import com.example.antikolektor.DataListStages
import com.example.antikolektor.DataProfile
import com.example.antikolektor.Server.Network
import okhttp3.Response

import okhttp3.ResponseBody
import java.util.*

class StagesRepository {
    suspend fun getUserStages(token:String): DataListStages? {
        val request = Network.apiClient.getUserStages(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun getProfile(token:String): DataProfile? {
        val request = Network.apiClient.getProfile(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }

    suspend fun getFeedback(token:String): String? {
        val request = Network.apiClient.getFeedback(token)

        if (request.isSuccessful) {
            return request.body()!!
        }
        return null
    }


    suspend fun getViewStages(id:Int,token:String): String? {
        val request = Network.apiClient.getViewStages(id,token)

        if (request.isSuccessful) {
//            Log.d("response", request.raw().toString())
            return request.body()?.string()
        }
        return null
    }

}