package com.example.evention.data.remote.users

import com.example.evention.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @GET("user/api/users")
    suspend fun getUsers(): List<User>

    @GET("user/api/users/my-profile")
    suspend fun getUserProfile(): User

    @DELETE("user/api/delete/{id}")
    suspend fun deleteUser(@Path("id") userId: String)

    @PUT("user/api/user/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body updatedFields: Map<String, Any?>
    ): User
}