package com.example.decise.data.networks.profile.changePassword


import com.google.gson.annotations.SerializedName

data class RequestChangePassword(
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("oldPassword")
    val oldPassword: String?
)