data class LoginRequest(
    val email: String,
    val password: String
)

data class GoogleLoginRequest(
    val token: String
)

data class ResetPasswordRequest(
    val email: String
)

