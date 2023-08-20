package com.example.decise.di

import com.example.decise.api.APIs.BASE_URL
import com.example.decise.api.AuthInterceptor
import com.example.decise.api.PublicApi
import com.example.decise.api.SecuredApi
import com.example.decise.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): PublicApi {
        return retrofitBuilder.build().create(PublicApi::class.java)
    }

    @Provides
    @Singleton
    fun providesDashboardApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): SecuredApi {
        return retrofitBuilder.client(okHttpClient).build().create(SecuredApi::class.java)
    }
    
}