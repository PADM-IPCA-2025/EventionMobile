package com.example.evention.data.remote.users

import com.example.evention.model.User

class UserRemoteDataSource(private val api: UserApiService) {
    suspend fun getUsers(): List<User> = api.getUsers()

    suspend fun getUserProfile(): User = api.getUserProfile()

    suspend fun deleteUser(userId: String) = api.deleteUser(userId)

    suspend fun updateUser(userId: String, username: String, email: String, phone: Int): User {
        val body = UserApiService.UpdateUserRequest(
            username = username,
            email = email,
            phone = phone,
        )
        return api.updateUser(userId, body)
    }
}
