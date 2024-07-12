package sa.cwad.screens.main.tabs.healthPlan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(val eventService: EventService) : ViewModel() {

    var date: LocalDate = LocalDate.now()

    fun hourEventsListForDate(selectDate:LocalDate): List<HourEvent> {

        val list = mutableListOf<HourEvent>()
        for (i in 0..23) {
            val time = LocalTime.of(i, 0)
            val events = eventService.eventsForDateAndTime(selectDate, time)
            val hourEvent = HourEvent(time, events)
            list.add(hourEvent)
        }
        return list
    }
}