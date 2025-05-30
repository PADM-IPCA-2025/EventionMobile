import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun getUnsafeOkHttpClient(userPreferences: UserPreferences): OkHttpClient {
    try {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(AuthInterceptor(userPreferences))
            .build()

    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

class AuthInterceptor(private val userPreferences: UserPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        //val token = userPreferences.getToken()
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOiJhMGJkZWM1NC05NmM3LTQwNjQtOGEzOS0xMWNmYmMyZmI3ZTIiLCJ1c2VybmFtZSI6ImNyaXN0aWFub3JvbmFsZG8iLCJlbWFpbCI6ImNyaXN0aWFub3JvbmFsZG9AZ21haWwuY29tIiwidXNlclR5cGUiOiIxMjNlNDU2Ny1lODliLTEyZDMtYTQ1Ni00MjY2MTQxNzQwMDIiLCJpYXQiOjE3NDg2MDkzNTQsImV4cCI6MTc0ODY5NTc1NH0.GXW9Nn13dKfsK2MZfqZ0QFg8ts-pWFQ0OQOD0qvBHy8"
        val requestBuilder = originalRequest.newBuilder()

        //if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", token)
        //}

        val requestWithAuth = requestBuilder.build()
        return chain.proceed(requestWithAuth)
    }
}
