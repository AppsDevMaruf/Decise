package com.example.decise.api


import com.example.decise.data.models.login.RequestLogin
import com.example.decise.data.models.verifyEmail.ResponseVerifyEmail
import com.example.decise.data.models.login.ResponseLogin
import com.example.decise.data.models.sendEmail.ResponseSendEmail
import com.example.decise.data.models.signUp.RequestSignUp
import com.example.decise.data.models.signUp.ResponseSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
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

   /* @POST("api/logout/")
    fun logOutUser(@Query("token") token: String?): Call<>*/
}