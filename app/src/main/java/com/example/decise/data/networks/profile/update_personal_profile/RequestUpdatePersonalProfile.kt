package com.example.decise.data.networks.profile.update_personal_profile


import com.example.decise.data.networks.profile.DecisionGroup
import com.google.gson.annotations.SerializedName

data class RequestUpdatePersonalProfile(
    @SerializedName("countryCode")
    val countryCode: String?,
    @SerializedName("decisionGroups")
    val decisionGroups: MutableSet<DecisionGroup>,
    @SerializedName("department")
    val department: String?,
    @SerializedName("designation")
    val designation: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?
)