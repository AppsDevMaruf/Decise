package com.example.decise.data.models.sendEmail


import com.google.gson.annotations.SerializedName

data class ResponseSendEmail(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)