package com.example.evention.di

import com.example.evention.data.remote.events.EventApiService
import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.data.remote.tickets.TicketApiService
import com.example.evention.data.remote.tickets.TicketRemoteDataSource
import getUnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://10.0.2.2:5010"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API
    private val eventApi: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }

    private val ticketApi: TicketApiService by lazy {
        retrofit.create(TicketApiService::class.java)
    }

    // Data Source
    val eventRemoteDataSource: EventRemoteDataSource by lazy {
        EventRemoteDataSource(eventApi)
    }

    val ticketRemoteDataSource: TicketRemoteDataSource by lazy {
        TicketRemoteDataSource(ticketApi)
    }
}


