class LoginRemoteDataSource(private val api: LoginApiService) {
    suspend fun login(email: String, password: String): LoginResponse {
        return api.login(LoginRequest(email, password))
    }
}
