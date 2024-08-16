package sa.cwad.screens.main.tabs.healthPlan.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sa.cwad.screens.main.tabs.healthPlan.models.room.entities.EventDbEntity

@Dao
interface EventsDao {

    @Query("SELECT id, name, date_time, account_id FROM events WHERE account_id = :id")
    fun findByAccountId(id: Long): Flow<List<EventDbEntity?>>

    @Insert(entity = EventDbEntity::class)
    suspend fun createEvent(eventDbEntity: EventDbEntity)

    @Query("SELECT id, name, date_time, account_id FROM events WHERE account_id = :accountId AND date(date_time / 1000, 'unixepoch') = :searchDate")
    fun findByAccountIdOnSpecificDay(accountId: Long, searchDate: String): Flow<List<EventDbEntity?>>
}
