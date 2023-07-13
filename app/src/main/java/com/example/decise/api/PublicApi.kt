package com.example.decise.api


import com.example.decise.data.models.auth.forgetPassword.ResponseForgetPassword
import com.example.decise.data.models.auth.login.RequestLogin
import com.example.decise.data.models.auth.verifyEmail.ResponseVerifyEmail
import com.example.decise.data.models.auth.login.ResponseLogin
import com.example.decise.data.models.auth.sendEmail.ResponseSendEmail
import com.example.decise.data.models.auth.signUp.RequestSignUp
import com.example.decise.data.models.auth.signUp.ResponseSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PublicApi {
    @POST("signup/complete-signup")
    suspend fun completeSignup(@Body requestRegister: RequestSignUp): Response<ResponseSignUp>

    @POST("signup/send-email")
    suspend fun sendEmail(@Query("email") email: String): Response<ResponseSendEmail>
    //subscriptionType=UNKNOWN&signupType=SELF&email=maru@yopmail.com&evc=523254
    @POST("signup/verify-email")
    suspend fun verifyEmail(
        @Query("subscriptionType") subscriptionType: String,
        @Query("signupType") signupType: String,
        @Query("email") email: String,
        @Query("evc") evc: String,

    ): Response<ResponseVerifyEmail>


    @POST("signin/")
    suspend fun login(@Body requestLogin: RequestLogin): Response<ResponseLogin>
    @PUT("profile/forgot-password")
    suspend fun forgotPassword(@Query("email") email: String): Response<ResponseForgetPassword>

    //subscription-controller

}