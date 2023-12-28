package com.example.antikolektor.Service

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.*
import com.example.antikolektor.RoomDatabase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.N)
class ScreeningService : CallScreeningService() {

    var blacklistednumber = arrayListOf<User>()
    var blacklistednumberBase = arrayListOf<Base>()
    lateinit var dataStore: DataStore<Preferences>

    override fun onScreenCall(details: Call.Details) {
        val repositorys = UserRepository(UserDatabase.getDatabase( this).userDao())
        dataStore = this.createDataStore(name = "settings")
        CoroutineScope(Dispatchers.IO).launch {
            val value = readToken("switch")
            when (value) {
                "true" -> {
                    blacklistednumber.clear()
                    blacklistednumberBase.clear()
                    blacklistednumber.addAll(repositorys.readAllDataNoLiveData())
                    blacklistednumberBase.addAll(repositorys.readAllDataBaseNoLiveData())
                    val phoneNumber = details.handle.schemeSpecificPart
                    blacklistednumber.forEach {
                        if ("+${it.phone}" == phoneNumber.toString()) {
                            disconnectCall(details, phoneNumber)

                        } else {
                            blacklistednumberBase.forEach {
                                if ("+${it.phone}" == phoneNumber.toString()) {
                                    disconnectCall(details, phoneNumber)
                                }
                            }
                        }
                    }
                }
                "false" -> {

                }
            }

        }
    }

    private fun disconnectCall(details: Call.Details, phoneNumber: String) {
        respondToCall(details, buildResponse())
    }

    private suspend fun readToken(key: String): String? {
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }

    private fun buildResponse(): CallResponse {
        return CallResponse.Builder()
            .setRejectCall(true)
            .setDisallowCall(true)
            .setSkipNotification(true)
            .setSkipCallLog(true)
            .build()
    }
}


