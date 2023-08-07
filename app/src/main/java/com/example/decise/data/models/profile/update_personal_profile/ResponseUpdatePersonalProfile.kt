package com.example.decise.data.models.profile.update_personal_profile


import com.google.gson.annotations.SerializedName

data class ResponseUpdatePersonalProfile(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("hasError")
    val hasError: Boolean?,
    @SerializedName("message")
    val message: String?
)