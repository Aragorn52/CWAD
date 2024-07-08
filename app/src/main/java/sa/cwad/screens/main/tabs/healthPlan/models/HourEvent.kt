package sa.cwad.screens.main.tabs.healthPlan.models

import sa.cwad.screens.main.tabs.healthPlan.models.Event
import java.time.LocalTime

data class HourEvent(
    val time: LocalTime,
    val events: List<Event>
) {
}