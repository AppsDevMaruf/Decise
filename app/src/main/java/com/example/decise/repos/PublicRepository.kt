package com.example.decise.repos

import com.example.decise.api.PublicApi
import com.example.decise.data.models.login.RequestLogin
import com.example.decise.data.models.sendEmail.RequestSendEmail
import com.example.decise.data.models.signUp.RequestSignUp
import javax.inject.Inject

class PublicRepository @Inject constructor(private val userApi: PublicApi) {


    suspend fun loginRepo(requestLogin: RequestLogin) = userApi.login(requestLogin)
    suspend fun sendEmailRepo(email: String) = userApi.sendEmail(email)
    suspend fun signUpRepo(requestSignUp: RequestSignUp) = userApi.signUp(requestSignUp)




}