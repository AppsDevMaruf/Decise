package com.example.decise.data.networks.decisions


import com.google.gson.annotations.SerializedName

data class ResponseDecisionList(
    @SerializedName("statusWiseCount")
    val statusWiseCount: List<StatusWiseCount?>?,
    @SerializedName("totalDecision")
    val totalDecision: Int?
) {
    data class StatusWiseCount(
        @SerializedName("status")
        val status: String?,
        @SerializedName("total")
        val total: Int?
    )
}