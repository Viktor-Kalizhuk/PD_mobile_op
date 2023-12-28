package com.example.antikolektor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.example.antikolektor.connect.ConnectivityObserver
import com.example.antikolektor.connect.NetworkConnectivityObserver
import com.example.antikolektor.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        lifecycleScope.launch {
            connect()
        }
    }

    suspend fun connect() {
        connectivityObserver.observe().collect {
            Snackbar.make(binding.NavHostFragvent, it.toString(), Snackbar.LENGTH_LONG).show()
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("inet", it.toString())
        }
    }


}