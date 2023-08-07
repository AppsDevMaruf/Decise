package com.example.decise.api

import com.example.decise.data.models.profile.departments.Departments
import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.profile.chooseSubscriptionType.ResponseChooseSubscriptionType
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

    @PUT("profile/choose-subscription-type")
    suspend fun chooseSubscriptionType(@Body chooseSubscriptionType: RequestChooseSubscriptionType): Response<ResponseChooseSubscriptionType>

    @GET("profile/personal/")
    suspend fun getPersonalProfile(@Query("id") id: Int): Response<ResponsePersonalProfile>

    @GET("/department/list/{companyId}")
    suspend fun getDepartmentList(@Path("companyId") companyId: Int): Response<List<Departments>>

    @GET("decision-group/list/{companyId}")
    suspend fun getDecisionGroupList(@Path("companyId") companyId: Int): Response<List<Departments>>

    @GET("designation/list/{companyId}")
    suspend fun getDesignationList(@Path("companyId") companyId: Int): Response<List<Departments>>

    @PUT("profile/update-personal")
    suspend fun updatePersonalProfile(@Body requestUpdatePersonalProfile: RequestUpdatePersonalProfile): Response<ResponseUpdatePersonalProfile>

    /*  @POST("/subscription/checkout")
      suspend fun checkoutSubscription(@Body request: CheckoutRequest): Response<CheckoutResponse>

      @GET("/subscription/confirm")
      suspend fun confirmSubscription(): Response<ConfirmationResponse>

      @POST("/subscription/do-transaction")
      suspend fun doTransaction(@Body request: TransactionRequest): Response<TransactionResponse>

      @POST("/subscription/payment")
      suspend fun makePayment(@Body request: PaymentRequest): Response<PaymentResponse>*/
}