package com.example.evention.di

import com.example.evention.data.remote.events.EventApiService
import com.example.evention.data.remote.events.EventRemoteDataSource
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

    // Data Source
    val eventRemoteDataSource: EventRemoteDataSource by lazy {
        EventRemoteDataSource(eventApi)
    }
}