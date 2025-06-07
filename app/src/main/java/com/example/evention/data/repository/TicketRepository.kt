import android.util.Log
import com.example.evention.data.local.dao.TicketDao
import com.example.evention.data.local.entities.TicketEntity
import com.example.evention.data.remote.tickets.TicketRemoteDataSource
import com.example.evention.model.TicketRaw
import kotlinx.coroutines.flow.Flow

class TicketRepository(
    private val remote: TicketRemoteDataSource,
    private val local: TicketDao
) {
    fun getLocalTickets(): Flow<List<TicketEntity>> = local.getAllTickets()

    suspend fun syncTickets() {
        val remoteTickets = remote.getTickets()
        Log.d("TicketRepository", "Tickets fetched from remote: ${remoteTickets.size}")

        val localEntities = remoteTickets.map {
            TicketEntity(
                ticketID = it.ticketID,
                event_id = it.event_id,
                user_id = it.user_id,
                feedback_id = it.feedback_id,
                participated = it.participated
            )
        }

        local.insertAll(localEntities)
        Log.d("TicketRepository", "Inserted tickets locally")

        localEntities.map { entity ->
            Log.d("TicketRepository", "Ticket inserido: $entity")
        }
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