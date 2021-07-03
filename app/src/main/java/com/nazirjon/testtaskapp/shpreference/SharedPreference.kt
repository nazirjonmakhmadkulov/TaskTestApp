package com.nazirjon.testtaskapp.shpreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    private val PREFS_NAME = "task_test"
    private val PREFS_TOKEN_VALUE = "token_value"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token_value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(PREFS_TOKEN_VALUE, token_value).apply()
    }

    fun getToken(): String? {
        return sharedPref.getString(PREFS_TOKEN_VALUE, null)
    }

    fun deleteToken() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(PREFS_NAME).clear().apply()
    }
}