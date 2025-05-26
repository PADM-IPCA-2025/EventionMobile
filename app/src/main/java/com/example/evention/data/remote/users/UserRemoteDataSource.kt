package com.example.evention.data.remote.users

import com.example.evention.model.User

class UserRemoteDataSource(private val api: UserApiService) {
    suspend fun getUsers(): List<User> = api.getUsers()

    suspend fun getUserById(userId: String): User = api.getUserById(userId)
}
