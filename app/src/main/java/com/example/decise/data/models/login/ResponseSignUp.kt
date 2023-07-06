package com.example.decise.data.models.login


import com.google.gson.annotations.SerializedName

data class ResponseSignUp(
    @SerializedName("errorMessage")
    val errorMessage: Any?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)