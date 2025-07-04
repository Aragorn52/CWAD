package sa.cwad.screens.main.tabs.healthPlan.models

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val name: String,
    val date: LocalDate,
    val time: LocalTime,
) {

    companion object {
        var eventsList: MutableList<Event> = mutableListOf()
        fun eventsForDate(date: LocalDate): MutableList<Event> {
            val events: MutableList<Event> = mutableListOf()
            eventsList.forEach {
                if (it.date == date) {
                    events.add(it)
                }
            }
            return events

        }

        fun eventsForDateAndTime(date: LocalDate, time: LocalTime): MutableList<Event> {
            val events: MutableList<Event> = mutableListOf()
            eventsList.forEach { event ->
                if (event.date == date && event.time.hour == time.hour) {
                    events.add(event)
                }
            }
            return events

        }
    }
}