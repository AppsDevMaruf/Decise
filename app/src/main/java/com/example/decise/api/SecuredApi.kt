package com.example.decise.api

import com.example.decise.data.models.profile.chooseSubscriptionType.RequestChooseSubscriptionType
import com.example.decise.data.models.profile.chooseSubscriptionType.ResponseChooseSubscriptionType
import com.example.decise.data.models.subscription.subscriptionList.ResponseSubscriptionsList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface SecuredApi {
    @GET("subscription/list")
    suspend fun getSubscriptionList(): Response<ResponseSubscriptionsList>
    @PUT("profile/choose-subscription-type")
    suspend fun chooseSubscriptionType(@Body chooseSubscriptionType: RequestChooseSubscriptionType): Response<ResponseChooseSubscriptionType>

  /*  @POST("/subscription/checkout")
    suspend fun checkoutSubscription(@Body request: CheckoutRequest): Response<CheckoutResponse>

    @GET("/subscription/confirm")
    suspend fun confirmSubscription(): Response<ConfirmationResponse>

    @POST("/subscription/do-transaction")
    suspend fun doTransaction(@Body request: TransactionRequest): Response<TransactionResponse>

    @POST("/subscription/payment")
    suspend fun makePayment(@Body request: PaymentRequest): Response<PaymentResponse>*/
}