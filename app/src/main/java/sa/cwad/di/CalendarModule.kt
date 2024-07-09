package sa.cwad.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sa.cwad.screens.main.tabs.healthPlan.CalendarUtils

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarModule {

//    @Binds
//    abstract fun bindCalendarUtils(): CalendarUtils
}