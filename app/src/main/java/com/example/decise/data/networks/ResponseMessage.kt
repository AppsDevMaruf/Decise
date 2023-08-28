package com.example.decise.data.networks


import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)