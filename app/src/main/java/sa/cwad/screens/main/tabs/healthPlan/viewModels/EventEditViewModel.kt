package sa.cwad.screens.main.tabs.healthPlan.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sa.cwad.screens.main.tabs.healthPlan.EventService
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EventEditViewModel @Inject constructor(val eventService: EventService) : ViewModel() {

    var date: LocalDate = LocalDate.now()

    fun hourEventsListForDate(): List<HourEvent> {

        val list = mutableListOf<HourEvent>()
        for (i in 0..23) {
            val time = LocalTime.of(i, 0)
            val events = eventService.eventsForDateAndTime(date, time)
            val hourEvent = HourEvent(time, events)
            list.add(hourEvent)
        }
        return list
    }

    fun saveEvent(eventName: String, date: LocalDate, time: LocalTime) {
        val newEvent = Event(eventName, date, time)
        eventService.eventsList.add(newEvent)
    }
}