package com.example.decise.data.models.subscription.subscriptionList


import com.google.gson.annotations.SerializedName

data class ResponseSubscriptionsList(
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("subscription")
    val subscription: List<Subscription?>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?
) {
    data class Subscription(
        @SerializedName("currencyCode")
        val currencyCode: String?,
        @SerializedName("currencySymbol")
        val currencySymbol: String?,
        @SerializedName("currentSubscriptionPlan")
        val currentSubscriptionPlan: Boolean?,
        @SerializedName("details")
        val details: String?,
        @SerializedName("discount")
        val discount: Double?,
        @SerializedName("features")
        val features: List<Feature?>?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("noOfUsersMax")
        val noOfUsersMax: Int?,
        @SerializedName("noOfUsersMin")
        val noOfUsersMin: Int?,
        @SerializedName("priceMonthly")
        val priceMonthly: Double?,
        @SerializedName("priceYearly")
        val priceYearly: Double?,
        @SerializedName("subscriptionType")
        val subscriptionType: String?,
        @SerializedName("title")
        val title: String?
    ) {
        data class Feature(
            @SerializedName("createdAt")
            val createdAt: String?,
            @SerializedName("createdBy")
            val createdBy: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("lastModifiedBy")
            val lastModifiedBy: String?,
            @SerializedName("lastModifiedDate")
            val lastModifiedDate: String?,
            @SerializedName("status")
            val status: Boolean?,
            @SerializedName("title")
            val title: String?
        )
    }
}