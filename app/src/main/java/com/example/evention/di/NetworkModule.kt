package com.example.evention.di

import com.example.evention.data.remote.events.EventApiService
import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.data.remote.users.UserApiService
import com.example.evention.data.remote.users.UserRemoteDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://localhost:5010/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API
    private val eventApi: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }

    private val userApi: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    // Data Source
    val eventRemoteDataSource: EventRemoteDataSource by lazy {
        EventRemoteDataSource(eventApi)
    }

    val userRemoteDataSource: UserRemoteDataSource by lazy {
        UserRemoteDataSource(userApi)
    }
}