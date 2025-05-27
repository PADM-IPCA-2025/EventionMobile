package com.example.evention.data.remote.users

import com.example.evention.model.User

class UserRemoteDataSource(private val api: UserApiService) {
    suspend fun getUsers(): List<User> = api.getUsers()

    suspend fun getUserById(userId: String): User = api.getUserById(userId)

    suspend fun deleteUser(userId: String) = api.deleteUser(userId)

    suspend fun updateUser(userId: String, username: String, email: String, phone: Int): User {
        val body = mapOf(
            "username" to username,
            "email" to email,
            "phone" to phone
        )
        return api.updateUser(userId, body)
    }

}
