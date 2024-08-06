package sa.cwad.screens.main.tabs.healthPlan.models.entities

import java.time.LocalTime

data class HourEvent(
    val time: LocalTime,
    val events: List<Event>
) {
}