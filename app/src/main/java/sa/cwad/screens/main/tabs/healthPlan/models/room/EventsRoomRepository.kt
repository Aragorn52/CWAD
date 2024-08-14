package sa.cwad.screens.main.tabs.healthPlan.models.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import javax.inject.Inject

class EventsRoomRepository @Inject constructor(private val eventsDao: EventsDao)
 : EventsRepository {

    override fun getEventsByAccountId(accountId: Long): Flow<List<Event?>>
    {
        return eventsDao.findByAccountId(accountId).map { it.map { it?.toEvent() } }
    }

}