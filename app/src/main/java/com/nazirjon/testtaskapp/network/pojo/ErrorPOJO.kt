package com.nazirjon.testtaskapp.network.pojo

import com.google.gson.annotations.SerializedName

data class Error(
        @SerializedName("error_code")
        val errorCode: Int,

        @SerializedName("error_msg")
        val errorMessage: String
)