package sa.cwad.screens.main.tabs.healthPlan.models

import kotlinx.coroutines.flow.Flow
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event

interface EventsRepository {

    fun getEventsByAccountId(accountId: Long): Flow<List<Event?>>

    suspend fun createEvent(event: Event, accountId: Long)
}