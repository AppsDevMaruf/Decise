package com.example.decise.repos

import com.example.decise.api.PublicApi
import com.example.decise.data.models.login.RequestLogin
import com.example.decise.data.models.signUp.RequestSignUp
import javax.inject.Inject

class PublicRepository @Inject constructor(private val userApi: PublicApi) {


    suspend fun loginRepo(requestLogin: RequestLogin) = userApi.login(requestLogin)

    suspend fun verifyEmailRepo(
        subscriptionType: String,
        signupType: String,
        email: String,
        evc: String
    ) = userApi.verifyEmail(subscriptionType, signupType, email, evc)

    suspend fun sendEmailRepo(email: String) = userApi.sendEmail(email)
    suspend fun completeSignUpRepo(requestSignUp: RequestSignUp) = userApi.completeSignup(requestSignUp)


}