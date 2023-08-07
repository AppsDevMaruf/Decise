package com.example.decise.repos

import com.example.decise.api.SecuredApi
import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.profile.chooseSubscriptionType.ResponseChooseSubscriptionType
import com.example.decise.data.models.profile.update_personal_profile.RequestUpdatePersonalProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import javax.inject.Inject

class SecuredRepository @Inject constructor(private val securedApi: SecuredApi) {
    suspend fun getSubscriptionListRepo() = securedApi.getSubscriptionList()
    suspend fun getPersonalProfileRepo(id: Int) = securedApi.getPersonalProfile(id)
    suspend fun getDepartmentListRepo(id: Int) = securedApi.getDepartmentList(id)
    suspend fun getDesignationListRepo(id: Int) = securedApi.getDesignationList(id)
    suspend fun getDecisionGroupListRepo(id: Int) = securedApi.getDecisionGroupList(id)
    suspend fun updatePersonalProfile(requestUpdatePersonalProfile: RequestUpdatePersonalProfile) =
        securedApi.updatePersonalProfile(requestUpdatePersonalProfile)

    suspend fun chooseSubscriptionTypeRepo(chooseSubscriptionType: RequestChooseSubscriptionType) =
        securedApi.chooseSubscriptionType(chooseSubscriptionType)

}