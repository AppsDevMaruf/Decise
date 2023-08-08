package com.example.decise.data.models.profile

import com.google.gson.annotations.SerializedName
data class DropDownModel(
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
