package com.example.decise.data.models.profile.chooseSubscriptionType


import com.google.gson.annotations.SerializedName

data class ResponseChooseSubscriptionType(
    @SerializedName("companyId")
    val companyId: Int?,
    @SerializedName("message")
    val message: String?
)