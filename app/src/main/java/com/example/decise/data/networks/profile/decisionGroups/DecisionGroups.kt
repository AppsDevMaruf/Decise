package com.example.decise.data.networks.profile.decisionGroups
import com.google.gson.annotations.SerializedName
data class DecisionGroups(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String?,
    @SerializedName("note")
    val note: String? = null,
    @SerializedName("status")
    val status: Boolean? = false
)
