package sa.cwad.screens.main.tabs.healthPlan.models

import kotlinx.coroutines.flow.Flow
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import java.time.LocalDate
import java.time.LocalDateTime

interface EventsRepository {

    fun getEventsByAccountId(accountId: Long): Flow<List<Event?>>

    fun getEventsByAccountIdAndDate(accountId: Long, date: LocalDate): Flow<List<Event?>>

    suspend fun createEvent(event: Event, accountId: Long)
}