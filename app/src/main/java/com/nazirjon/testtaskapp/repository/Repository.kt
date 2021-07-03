package com.nazirjon.testtaskapp.repository

import android.content.Context
import com.nazirjon.testtaskapp.connection.Connection
import com.nazirjon.testtaskapp.network.APIService
import com.nazirjon.testtaskapp.network.pojo.Payment
import com.nazirjon.testtaskapp.shpreference.SharedPreference

class Repository(private val context: Context,
                 private val connection: Connection,
                 private val sharedPreference: SharedPreference,
                 private val apiService: APIService) {

    suspend fun signIn(login: String, password: String) : String? {
        connection.isConnectedToInternet.let {
            if (it) {
                val loginData = mapOf(
                    "login" to login,
                    "password" to password
                )
                val response = apiService.signIn(loginData)
                if (response.success == "true") {
                    response.response?.let { resp ->
                        sharedPreference.saveToken(resp.token)
                        return resp.token
                    }
                }
            }
        }
        return null
    }

    suspend fun payments(token: String): List<Payment> {
        connection.isConnectedToInternet.let {
            if (it) {
                val response = apiService.payments(token)
                if (response.success == "true") {
                    response.response?.let { resp ->
                        return resp
                    }
                }
            }
        }
        return listOf()
    }
}