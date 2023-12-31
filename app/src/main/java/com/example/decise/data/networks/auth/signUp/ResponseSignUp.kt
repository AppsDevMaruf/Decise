package com.example.decise.data.networks.auth.signUp


import com.google.gson.annotations.SerializedName

data class ResponseSignUp(
    @SerializedName("errorMessage")
    val errorMessage: Any?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)