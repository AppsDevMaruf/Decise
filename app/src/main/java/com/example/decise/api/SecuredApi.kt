package com.example.decise.api

import com.example.decise.api.APIs.CHANGE_PASSWORD
import com.example.decise.api.APIs.CHOOSE_SUBSCRIPTION_TYPE
import com.example.decise.api.APIs.DECISION_GROUP_LIST
import com.example.decise.api.APIs.DEPARTMENT_LIST
import com.example.decise.api.APIs.PERSONAL
import com.example.decise.api.APIs.UPDATE_PERSONAL
import com.example.decise.data.models.ResponseMessage
import com.example.decise.data.models.notifications.ResponseFindByCompanyIdAndStatus
import com.example.decise.data.models.profile.changePassword.RequestChangePassword
import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.profile.chooseSubscriptionType.ResponseChooseSubscriptionType
import com.example.decise.data.models.profile.decisionGroups.DecisionGroups
import com.example.decise.data.models.profile.departments.Departments
import com.example.decise.data.models.profile.designations.Designations
import com.example.decise.data.models.profile.personalProfileResponse.ResponsePersonalProfile
import com.example.decise.data.models.profile.update_personal_profile.RequestUpdatePersonalProfile
import com.example.decise.data.models.profile.update_personal_profile.ResponseUpdatePersonalProfile
import com.example.decise.data.models.subscription.subscriptionList.ResponseSubscriptionsList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SecuredApi {
    @GET("subscription/list")
    suspend fun getSubscriptionList(): Response<ResponseSubscriptionsList>

    @GET(PERSONAL)
    suspend fun getPersonalProfile(@Query("id") id: Int): Response<ResponsePersonalProfile>

    @PUT(CHOOSE_SUBSCRIPTION_TYPE)
    suspend fun chooseSubscriptionType(@Body chooseSubscriptionType: RequestChooseSubscriptionType): Response<ResponseChooseSubscriptionType>

    @PUT(UPDATE_PERSONAL)
    suspend fun updatePersonalProfile(@Body requestUpdatePersonalProfile: RequestUpdatePersonalProfile): Response<ResponseUpdatePersonalProfile>

    @PUT(CHANGE_PASSWORD)
    suspend fun changePassword(@Body req: RequestChangePassword): Response<ResponseMessage>

    @GET(DEPARTMENT_LIST)
    suspend fun getDepartmentList(@Path("companyId") companyId: Int): Response<List<Departments>>

    @GET(DECISION_GROUP_LIST)
    suspend fun getDecisionGroupList(@Path("companyId") companyId: Int): Response<List<DecisionGroups>>

    @GET(DECISION_GROUP_LIST)
    suspend fun getDesignationList(@Path("companyId") companyId: Int): Response<List<Designations>>


    //notification
    @GET("notification/findByCompanyIdAndStatus")
    suspend fun getNotificationByCompanyIdAndStatus(
        @Query("companyId") companyId: Int,
        @Query("status") status: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseFindByCompanyIdAndStatus>


}