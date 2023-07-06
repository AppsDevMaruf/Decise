package com.example.decise.api


import com.example.decise.data.models.login.RequestLogin
import com.example.decise.data.models.login.RequestSignUp
import com.example.decise.data.models.login.ResponseLogin
import com.example.decise.data.models.login.ResponseSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PublicApi {
    @POST("auth/register")
    suspend fun signUp(@Body requestRegister: RequestSignUp): Response<ResponseSignUp>

    @POST("signin/")
    suspend fun login(@Body requestLogin: RequestLogin): Response<ResponseLogin>

   /* @POST("api/logout/")
    fun logOutUser(@Query("token") token: String?): Call<>*/
}