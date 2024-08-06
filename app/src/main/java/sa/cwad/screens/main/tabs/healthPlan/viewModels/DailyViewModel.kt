package sa.cwad.screens.main.tabs.healthPlan.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sa.cwad.screens.main.tabs.healthPlan.EventService
import sa.cwad.screens.main.tabs.healthPlan.models.entities.HourEvent
import java.time.LocalDate
import java.time.LocalTime
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