package sa.cwad.screens.main.tabs.healthPlan.models.entities

import java.time.LocalDate

data class EventForDate(
    val date: LocalDate,
    val hourEvent: List<HourEvent>
) {
}