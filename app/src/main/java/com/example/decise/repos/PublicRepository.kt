package com.example.decise.repos

import com.example.decise.api.PublicApi
import com.example.decise.data.models.login.RequestLogin
import com.example.decise.data.models.login.RequestSignUp
import javax.inject.Inject

class PublicRepository @Inject constructor(private val userApi: PublicApi) {


    suspend fun loginRepo(requestLogin: RequestLogin) = userApi.login(requestLogin)
    suspend fun signUpRepo(requestSignUp: RequestSignUp) = userApi.signUp(requestSignUp)




}