package sa.cwad.screens.main.tabs.healthPlan.models.entities

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val name: String,
    val date: LocalDate,
    val time: LocalTime,
)