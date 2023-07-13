package com.example.decise.data.models.auth.login


import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
)