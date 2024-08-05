package sa.cwad.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import sa.cwad.model.accounts.room.AccountsDao
import sa.cwad.model.accounts.room.entities.AccountDbEntity
import sa.cwad.model.boxes.room.BoxesDao
import sa.cwad.model.boxes.room.entities.AccountBoxSettingDbEntity
import sa.cwad.model.boxes.room.entities.BoxDbEntity

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao(): BoxesDao

}