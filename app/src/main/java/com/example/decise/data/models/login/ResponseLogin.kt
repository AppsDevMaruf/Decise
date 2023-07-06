package com.example.decise.data.models.login


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("companyId")
    val companyId: Any?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstLogin")
    val firstLogin: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("refreshToken")
    val refreshToken: Any?,
    @SerializedName("rights")
    val rights: List<String?>?,
    @SerializedName("roles")
    val roles: List<String?>?,
    @SerializedName("subscriptionStatus")
    val subscriptionStatus: String?,
    @SerializedName("subscriptionType")
    val subscriptionType: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?
)