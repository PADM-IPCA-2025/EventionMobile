package com.example.evention.data.remote.authentication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("/user/api/users")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}