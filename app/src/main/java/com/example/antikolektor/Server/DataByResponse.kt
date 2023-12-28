package com.example.antikolektor.Server

import com.google.gson.annotations.SerializedName

data class DataByResponse(
    val data: List<Data>

)

data class Data(
    val id: Int,
    val type: String,
    val phone: String,
    val subtype: String,
    val comment: String?
)

data class Code(
    val code: String,
    val status: String
)

data class DataPostPhone(
    val phone: String,
    val comment: String,
    val type: String,
    val subtype: String,

)


