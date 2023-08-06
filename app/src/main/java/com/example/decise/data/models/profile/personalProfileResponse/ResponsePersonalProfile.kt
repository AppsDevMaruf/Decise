package com.example.decise.data.models.profile.personalProfileResponse


import com.google.gson.annotations.SerializedName

data class ResponsePersonalProfile(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("countryCode")
    val countryCode: String?,
    @SerializedName("decisionGroups")
    val decisionGroups: List<String?>?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("designation")
    val designation: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("expiredAt")
    val expiredAt: String?,
    @SerializedName("firstLogin")
    val firstLogin: Boolean?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastActive")
    val lastActive: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("needPasswordChange")
    val needPasswordChange: Boolean?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("profilePhoto")
    val profilePhoto: String?,
    @SerializedName("roles")
    val roles: List<String?>?,
    @SerializedName("subscriptionDate")
    val subscriptionDate: String?,
    @SerializedName("subscriptionLeftDays")
    val subscriptionLeftDays: Int?,
    @SerializedName("subscriptionType")
    val subscriptionType: String?,
    @SerializedName("userStatus")
    val userStatus: String?
)