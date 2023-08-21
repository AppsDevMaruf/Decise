package com.example.decise.api


import com.example.decise.api.APIs.COMPLETE_SIGNUP
import com.example.decise.api.APIs.FORGOT_PASSWORD
import com.example.decise.api.APIs.SEND_EMAIL
import com.example.decise.api.APIs.SIGNIN
import com.example.decise.api.APIs.VERIFY_EMAIL
import com.example.decise.data.models.auth.forgetPassword.ResponseForgetPassword
import com.example.decise.data.models.auth.login.RequestLogin
import com.example.decise.data.models.auth.login.ResponseLogin
import com.example.decise.data.models.auth.sendEmail.ResponseSendEmail
import com.example.decise.data.models.auth.signUp.RequestSignUp
import com.example.decise.data.models.auth.signUp.ResponseSignUp
import com.example.decise.data.models.auth.verifyEmail.ResponseVerifyEmail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PublicApi {
    @POST(COMPLETE_SIGNUP)
    suspend fun completeSignup(@Body requestRegister: RequestSignUp): Response<ResponseSignUp>

    @POST(SEND_EMAIL)
    suspend fun sendEmail(
        @Query("email") email: String,
        @Query("trialPeriod") trialPeriod: Int = 30,
        @Query("specialInvitation") specialInvitation: Boolean = false,
    ): Response<ResponseSendEmail>

    //subscriptionType=UNKNOWN&signupType=SELF&email=maru@yopmail.com&evc=523254
    @POST(VERIFY_EMAIL)
    suspend fun verifyEmail(
        @Query("subscriptionType") subscriptionType: String,
        @Query("signupType") signupType: String,
        @Query("email") email: String,
        @Query("evc") evc: String,

        ): Response<ResponseVerifyEmail>


    @POST(SIGNIN)
    suspend fun login(@Body requestLogin: RequestLogin): Response<ResponseLogin>

    @PUT(FORGOT_PASSWORD)
    suspend fun forgotPassword(@Query("email") email: String): Response<ResponseForgetPassword>

    //subscription-controller

}