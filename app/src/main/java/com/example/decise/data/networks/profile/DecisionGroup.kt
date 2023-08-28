package com.example.decise.data.networks.profile

import com.google.gson.annotations.SerializedName

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