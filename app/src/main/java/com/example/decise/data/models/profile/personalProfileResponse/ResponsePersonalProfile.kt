package com.example.decise.data.models.profile.personalProfileResponse


import com.google.gson.annotations.SerializedName

data class ResponsePersonalProfile(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("countryCode")
    val countryCode: Any?,
    @SerializedName("decisionGroups")
    val decisionGroups: List<Any?>?,
    @SerializedName("department")
    val department: Any?,
    @SerializedName("designation")
    val designation: Any?,
    @SerializedName("dob")
    val dob: Any?,
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
    val phoneNumber: Any?,
    @SerializedName("profilePhoto")
    val profilePhoto: String?,
    @SerializedName("roles")
    val roles: List<String?>?,
    @SerializedName("subscriptionDate")
    val subscriptionDate: Any?,
    @SerializedName("subscriptionLeftDays")
    val subscriptionLeftDays: Int?,
    @SerializedName("subscriptionType")
    val subscriptionType: String?,
    @SerializedName("userStatus")
    val userStatus: String?
)