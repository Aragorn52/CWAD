package sa.cwad.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sa.cwad.model.room.AppDatabase
import sa.cwad.model.settings.AppSettings
import sa.cwad.model.settings.SharedPreferencesAppSettings
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.room.EventsDao
import sa.cwad.screens.main.tabs.healthPlan.models.room.EventsRoomRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Binds
//    abstract fun bindCalendarUtils(): CalendarUtils

    @Provides
    fun provideEventDao(@ApplicationContext application: Context): EventsDao {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
        return db.getEventsDao()
    }

    @Singleton
    @Provides
    fun provideEventRepository(eventDao: EventsDao): EventsRepository {
        return EventsRoomRepository(eventDao)
    }

    @Singleton
    @Provides
    fun provideAppSettings(@ApplicationContext application: Context): AppSettings {
        return SharedPreferencesAppSettings(application)
    }
}