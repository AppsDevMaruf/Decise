package com.example.decise.data.networks.auth.forgetPassword


import com.google.gson.annotations.SerializedName

data class ResponseForgetPassword(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)