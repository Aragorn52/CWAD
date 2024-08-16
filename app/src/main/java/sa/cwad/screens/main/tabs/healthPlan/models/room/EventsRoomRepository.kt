package sa.cwad.screens.main.tabs.healthPlan.models.room

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import sa.cwad.screens.main.tabs.healthPlan.models.room.entities.EventDbEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventsRoomRepository @Inject constructor(private val eventsDao: EventsDao)
 : EventsRepository {

    override fun getEventsByAccountId(accountId: Long): Flow<List<Event?>>
    {
        return eventsDao.findByAccountId(accountId).map { it.map { it?.toEvent() } }
    }

    override fun getEventsByAccountIdAndDate(accountId: Long, date: LocalDate): Flow<List<Event?>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val searchDate = date.format(formatter)
        return eventsDao.findByAccountIdOnSpecificDay(accountId, searchDate).map { it.map { it?.toEvent() } }
    }

    override suspend fun createEvent(event: Event, accountId: Long) {
        val eventDb = EventDbEntity.fromEvent(event).also { it.accountId = accountId }
        eventsDao.createEvent(eventDb)
    }

}