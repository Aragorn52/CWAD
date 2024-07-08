package sa.cwad.screens.main.tabs.healthPlan

import java.time.LocalTime

data class HourEvent(
    val time: LocalTime,
    val events: List<Event>
) {
}