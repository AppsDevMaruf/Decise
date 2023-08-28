package com.example.decise.repos

import com.example.decise.api.PublicApi
import com.example.decise.data.networks.auth.login.RequestLogin
import com.example.decise.data.networks.auth.signUp.RequestSignUp
import javax.inject.Inject

class PublicRepository @Inject constructor(private val publicApi: PublicApi) {


    suspend fun loginRepo(requestLogin: RequestLogin) = publicApi.login(requestLogin)

    suspend fun verifyEmailRepo(
        subscriptionType: String,
        signupType: String,
        email: String,
        evc: String
    ) = publicApi.verifyEmail(subscriptionType, signupType, email, evc)

    suspend fun sendEmailRepo(email: String) = publicApi.sendEmail(email)
    suspend fun forgotPasswordRepo(email: String) = publicApi.forgotPassword(email)
    suspend fun completeSignUpRepo(requestSignUp: RequestSignUp) = publicApi.completeSignup(requestSignUp)


}