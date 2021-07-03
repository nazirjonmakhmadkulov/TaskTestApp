package com.nazirjon.testtaskapp

import android.app.Application
import com.nazirjon.testtaskapp.connection.Connection
import com.nazirjon.testtaskapp.repository.Repository
import com.nazirjon.testtaskapp.network.APIService
import com.nazirjon.testtaskapp.shpreference.SharedPreference

class App: Application() {
    private val connectionManager by lazy { Connection(this) }
    private val apiService by lazy { APIService.create() }
    val sharedPreference by lazy { SharedPreference(this) }
    val repository by lazy { Repository(this, connectionManager, sharedPreference, apiService) }
}