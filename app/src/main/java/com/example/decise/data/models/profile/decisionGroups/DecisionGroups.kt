package com.example.decise.data.models.profile.decisionGroups
import com.google.gson.annotations.SerializedName
data class DecisionGroups(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("note")
    val note: Any?,
    @SerializedName("status")
    val status: Boolean?
)
