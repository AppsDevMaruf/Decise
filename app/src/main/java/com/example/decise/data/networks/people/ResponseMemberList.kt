package com.example.decise.data.networks.people


import com.google.gson.annotations.SerializedName

class ResponseMemberList : ArrayList<ResponseMemberListItem>(){
    data class ResponseMemberListItem(
        @SerializedName("companyId")
        val companyId: Int?,
        @SerializedName("decisionGroups")
        val decisionGroups: List<Any?>?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("firstName")
        val firstName: String?,
        @SerializedName("fullName")
        val fullName: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("lastActive")
        val lastActive: String?,
        @SerializedName("lastName")
        val lastName: String?,
        @SerializedName("profileImage")
        val profileImage: String?,
        @SerializedName("roles")
        val roles: List<String?>?
    )
}