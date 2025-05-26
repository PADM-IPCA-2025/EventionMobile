class LoginRemoteDataSource(private val api: LoginApiService) {
    suspend fun login(email: String, password: String): LoginResponse {
        val response = api.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Resposta vazia")
        } else {
            val error = response.errorBody()?.string() ?: "Erro desconhecido"
            throw Exception("Erro no login: $error")
        }
    }
}
