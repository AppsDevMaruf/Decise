package com.example.decise.data.networks.auth.login


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("companyDtoList")
    val companyDtoList: List<CompanyDto?>?,
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstLogin")
    val firstLogin: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("participantRights")
    val participantRights: List<String?>?,
    @SerializedName("participantToken")
    val participantToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("rights")
    val rights: List<String?>?,
    @SerializedName("roles")
    val roles: List<String?>?,
    @SerializedName("subscriptionLeftDays")
    val subscriptionLeftDays: Int?,
    @SerializedName("subscriptionStatus")
    val subscriptionStatus: String?,
    @SerializedName("subscriptionType")
    val subscriptionType: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("type")
    val type: String?
) {
    data class CompanyDto(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?
    )
}