import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/user/api/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
