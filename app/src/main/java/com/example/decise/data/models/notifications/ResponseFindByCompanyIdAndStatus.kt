package com.example.decise.data.models.notifications


import com.google.gson.annotations.SerializedName

data class ResponseFindByCompanyIdAndStatus(
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("hasNext")
    val hasNext: Boolean?,
    @SerializedName("notifications")
    val notifications: List<Notification?>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?
) {
    data class Notification(
        @SerializedName("commentId")
        val commentId: Any?,
        @SerializedName("companyId")
        val companyId: Int?,
        @SerializedName("decisionCustomId")
        val decisionCustomId: Any?,
        @SerializedName("decisionGroupId")
        val decisionGroupId: Any?,
        @SerializedName("decisionId")
        val decisionId: Int?,
        @SerializedName("decisionName")
        val decisionName: Any?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("module")
        val module: Any?,
        @SerializedName("notificationPurpose")
        val notificationPurpose: Any?,
        @SerializedName("notificationText")
        val notificationText: Any?,
        @SerializedName("notificationTitle")
        val notificationTitle: String?,
        @SerializedName("notifiedAt")
        val notifiedAt: String?,
        @SerializedName("notifiedBy")
        val notifiedBy: NotifiedBy?,
        @SerializedName("notifiedTo")
        val notifiedTo: Any?,
        @SerializedName("notifiedUsers")
        val notifiedUsers: List<Any?>?,
        @SerializedName("questionId")
        val questionId: Any?,
        @SerializedName("questionSetId")
        val questionSetId: Any?,
        @SerializedName("status")
        val status: Boolean?
    ) {
        data class NotifiedBy(
            @SerializedName("email")
            val email: String?,
            @SerializedName("firstName")
            val firstName: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("lastName")
            val lastName: String?,
            @SerializedName("profileImage")
            val profileImage: String?
        )
    }
}