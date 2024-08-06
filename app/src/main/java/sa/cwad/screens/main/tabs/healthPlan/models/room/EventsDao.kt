package sa.cwad.screens.main.tabs.healthPlan.models.room

import androidx.room.Dao
import androidx.room.Query
import sa.cwad.screens.main.tabs.healthPlan.models.room.entities.EventDbEntity

@Dao
interface EventsDao {

    @Query("SELECT * FROM events WHERE account_id = :id")
    suspend fun findByAccountId(id: Long): List<EventDbEntity?>
}
