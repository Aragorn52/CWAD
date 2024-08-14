package sa.cwad

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sa.cwad.model.accounts.AccountsRepository
import sa.cwad.model.accounts.room.RoomAccountsRepository
import sa.cwad.model.boxes.BoxesRepository
import sa.cwad.model.boxes.room.RoomBoxesRepository
import sa.cwad.model.room.AppDatabase
import sa.cwad.model.settings.AppSettings
import sa.cwad.model.settings.SharedPreferencesAppSettings
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.room.EventsRoomRepository


object Repositories {

    private lateinit var applicationContext: Context

    // -- stuffs

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    // --- repositories

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        RoomBoxesRepository(accountsRepository, database.getBoxesDao(), ioDispatcher)
    }

    val eventsRepository: EventsRepository by lazy {
        EventsRoomRepository(database.getEventsDao())
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(context: Context) {
        applicationContext = context
    }
}