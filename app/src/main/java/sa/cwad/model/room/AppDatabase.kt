package sa.cwad.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sa.cwad.model.accounts.room.AccountsDao
import sa.cwad.model.accounts.room.entities.AccountDbEntity
import sa.cwad.model.boxes.room.BoxesDao
import sa.cwad.model.boxes.room.entities.AccountBoxSettingDbEntity
import sa.cwad.model.boxes.room.entities.BoxDbEntity
import sa.cwad.screens.main.tabs.healthPlan.models.room.EventsDao
import sa.cwad.screens.main.tabs.healthPlan.models.room.entities.EventDbEntity
import sa.cwad.utils.DateTimeConverters

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingDbEntity::class,
        EventDbEntity::class
    ]
)
@TypeConverters(DateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao(): BoxesDao

    abstract fun getEventsDao(): EventsDao
}