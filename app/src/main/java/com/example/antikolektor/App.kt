package com.example.antikolektor

import android.app.Application
import com.example.antikolektor.di.AppComponent
import com.example.antikolektor.di.DaggerAppComponent


class App :Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder().build()
    }

}