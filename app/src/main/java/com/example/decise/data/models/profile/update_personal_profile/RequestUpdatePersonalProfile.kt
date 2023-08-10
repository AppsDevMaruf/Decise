package com.example.decise.data.models.profile.update_personal_profile


import com.google.gson.annotations.SerializedName

data class RequestUpdatePersonalProfile(
    @SerializedName("countryCode")
    val countryCode: String?,
    @SerializedName("decisionGroups")
    val decisionGroups: List<DecisionGroup?>?,
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
) {
    data class DecisionGroup(
        @SerializedName("companyId")
        val companyId: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("note")
        val note: String?,
        @SerializedName("status")
        val status: Boolean?
    )
}