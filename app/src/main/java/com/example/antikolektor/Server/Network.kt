package com.example.antikolektor.Server

import android.text.Editable
import android.text.TextWatcher
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object Network {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://lk.pravoe-delo.su/api/v1/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()
    val rickAndMortyService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    val apiClient = ApiClient(rickAndMortyService)
}

object NetworkDirectory {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://suggestions.dadata.ru/suggestions/api/4_1/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()
    val rickAndMortyService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    val apiClient = ApiClient(rickAndMortyService)
}






