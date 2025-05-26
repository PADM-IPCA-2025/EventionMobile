package com.example.evention.data.remote.users

import com.example.evention.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("user/api/users")
    suspend fun getUsers(): List<User>

    @GET("user/api/user/{id}")
    suspend fun getUserById(@Path("id") userId: String): User
}