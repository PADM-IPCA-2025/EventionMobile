import com.example.evention.data.remote.events.EventApiService
import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.repository.events.EventRepository
import com.example.evention.repository.events.EventRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
        private const val BASE_URL = "https://localhost:5010/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // APIs
    private val eventApi = retrofit.create(EventApiService::class.java)

    // Data Sources
    private val eventRemote = EventRemoteDataSource(eventApi)

    // Repositories
    val eventRepository: EventRepository by lazy { EventRepositoryImpl(eventRemote) }
}
