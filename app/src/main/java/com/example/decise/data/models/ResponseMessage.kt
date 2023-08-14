package com.example.decise.data.models


import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)