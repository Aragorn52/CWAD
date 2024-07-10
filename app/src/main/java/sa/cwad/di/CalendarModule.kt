package sa.cwad.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarModule {

//    @Binds
//    abstract fun bindCalendarUtils(): CalendarUtils
}