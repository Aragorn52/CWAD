package sa.cwad.screens.main.tabs.healthPlan.models

import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event

interface EventsRepository {

    suspend fun getEventsByAccountId(accountId: Long): List<Event?>
}