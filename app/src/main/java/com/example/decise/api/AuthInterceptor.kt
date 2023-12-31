package com.example.decise.api

import android.content.Context
import com.example.decise.data.prefs.TokenManager
import com.example.decise.utils.Constants
import com.example.decise.utils.NoInternetException
import com.example.decise.utils.isConnectedToNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(@ApplicationContext var appContext: Context) :
    Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager


    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectedToNetwork(appContext)) {
            throw NoInternetException("Make sure You have an active internet connection !")
        } else {
            val request = chain.request().newBuilder()
            val token = tokenManager.getToken(Constants.TOKEN)
            request.addHeader("Authorization", token)
            return chain.proceed(request.build())
        }

    }


}