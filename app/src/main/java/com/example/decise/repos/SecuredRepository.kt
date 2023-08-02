package com.example.decise.repos

import com.example.decise.api.SecuredApi
import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.profile.chooseSubscriptionType.ResponseChooseSubscriptionType
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import javax.inject.Inject

class SecuredRepository @Inject constructor(private val securedApi: SecuredApi) {

    /*
        suspend fun logout() = securedApi.logout()
        suspend fun getTransactionHistory() = securedApi.getTransactionHistory()*/
    suspend fun getSubscriptionListRepo() = securedApi.getSubscriptionList()
    suspend fun getPersonalProfileRepo(id: Int) = securedApi.getPersonalProfile(id)

    suspend fun chooseSubscriptionTypeRepo(chooseSubscriptionType: RequestChooseSubscriptionType) =
        securedApi.chooseSubscriptionType(chooseSubscriptionType)

}