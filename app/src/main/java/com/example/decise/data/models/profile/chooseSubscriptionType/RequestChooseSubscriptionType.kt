package com.example.decise.data.models.profile.chooseSubscriptionType


import com.google.gson.annotations.SerializedName

data class RequestChooseSubscriptionType(
    @SerializedName("cardHolderName")
    val cardHolderName: String?,
    @SerializedName("cardNumber")
    val cardNumber: String?,
    @SerializedName("cvvNumber")
    val cvvNumber: String?,
    @SerializedName("durationType")
    val durationType: String?,
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @SerializedName("subscriptionPeriodInDays")
    val subscriptionPeriodInDays: Int?,
    @SerializedName("subscriptionType")
    val subscriptionType: String?
)