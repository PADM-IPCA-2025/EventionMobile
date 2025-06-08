import android.util.Log
import com.example.evention.data.local.dao.TicketDao
import com.example.evention.data.local.entities.EventEntity
import com.example.evention.data.local.entities.TicketEntity
import com.example.evention.data.remote.events.EventRemoteDataSource
import com.example.evention.data.remote.tickets.TicketRemoteDataSource
import com.example.evention.model.TicketRaw
import kotlinx.coroutines.flow.Flow

class TicketRepository(
    private val remote: TicketRemoteDataSource,
    private val eventRemote: EventRemoteDataSource,
    private val local: TicketDao
) {
    fun getLocalTickets(): Flow<List<TicketEntity>> = local.getAllTickets()

    suspend fun syncTickets() {
        val remoteTickets = remote.getTickets()
        Log.d("TicketRepository", "Tickets fetched from remote: ${remoteTickets.size}")

        val localTicketEntities = remoteTickets.map { ticket ->
            TicketEntity(
                ticketID = ticket.ticketID,
                event_id = ticket.event_id,
                user_id = ticket.user_id,
                feedback_id = ticket.feedback_id,
                participated = ticket.participated
            )
        }

        val uniqueEventIds = remoteTickets.map { it.event_id }.toSet()

        val localEventEntities = uniqueEventIds.mapNotNull { eventId ->
            try {
                val remoteEvent = eventRemote.getEventById(eventId)
                EventEntity(
                    eventID = remoteEvent.eventID,
                    userId = remoteEvent.userId,
                    name = remoteEvent.name,
                    description = remoteEvent.description,
                    startAt = remoteEvent.startAt.toString(),
                    endAt = remoteEvent.endAt.toString(),
                    price = remoteEvent.price,
                    eventPicture = remoteEvent.eventPicture
                )
            } catch (e: Exception) {
                Log.e("TicketRepository", "Erro ao obter evento $eventId: ${e.message}")
                null
            }
        }

        local.insertAll(localTicketEntities)
        local.insertAllEvents(localEventEntities)

        Log.d(
            "TicketRepository",
            "Inserted ${localTicketEntities.size} tickets and ${localEventEntities.size} events locally"
        )
    }

    suspend fun getLocalEventById(eventId: String): EventEntity? {
        return local.getEventById(eventId)
    }



    suspend fun getTicketById(ticketId: String): TicketEntity? {
        return local.getTicketById(ticketId)
    }

    suspend fun createTicket(eventId: String) {
        val newTicket = remote.createTicket(eventId)

        val userId = newTicket.user_id ?: throw IllegalStateException("User ID is null")
        val feedbackId = newTicket.feedback_id ?: throw IllegalStateException("Feedback ID is null")
        val participated = newTicket.participated ?: false

        val entity = TicketEntity(
            ticketID = newTicket.ticketID,
            event_id = newTicket.event.eventID,
            user_id = userId,
            feedback_id = feedbackId,
            participated = participated
        )
        local.insert(entity)
    }

}