package sa.cwad.screens.main.tabs.healthPlan.models.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import java.time.LocalDateTime

@Entity(
    tableName = "events"
)
data class EventDbEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date_time") val eventDateTime: LocalDateTime,
    @ColumnInfo(name = "account_id") val accountId: Long,
) {
    fun toEvent(): Event = Event(
        name = name,
        date = eventDateTime.toLocalDate(),
        time = eventDateTime.toLocalTime()
    )
}