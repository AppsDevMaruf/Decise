package com.example.decise.data.networks.auth.sendEmail


import com.google.gson.annotations.SerializedName

data class ResponseSendEmail(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)