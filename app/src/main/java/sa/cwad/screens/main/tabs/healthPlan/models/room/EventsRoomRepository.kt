package sa.cwad.screens.main.tabs.healthPlan.models.room

import kotlinx.coroutines.CoroutineDispatcher
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event

class EventsRoomRepository(
    private val eventsDao: EventsDao,
    private val ioDispatcher: CoroutineDispatcher
) : EventsRepository {

    override suspend fun getEventsByAccountId(accountId: Long): List<Event?>
    {
        return eventsDao.findByAccountId(accountId).map { it?.toEvent() }
    }

}