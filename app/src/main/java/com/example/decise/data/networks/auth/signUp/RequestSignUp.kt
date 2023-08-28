package com.example.decise.data.networks.auth.signUp


import com.google.gson.annotations.SerializedName

data class RequestSignUp(
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("signUpType")
    val signUpType: String?,
    @SerializedName("subscriptionType")
    val subscriptionType: String?
)