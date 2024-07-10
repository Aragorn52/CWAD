package sa.cwad.screens.main.tabs.healthPlan

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatePresenter @Inject constructor() {

    fun monthYearFromDate(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("MMM yyyy", Locale("ru"))
        return date.format(dateFormat)
    }

    fun monthDayFromDate(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("MMM d", Locale("ru"))
        return date.format(dateFormat)
    }

    fun formattedDate(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("ru"))
        return date.format(dateFormat)
    }

    fun formattedTime(time: LocalTime): String {
        val timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale("ru"))
        return time.format(timeFormat)
    }

    fun formattedShortTime(time: LocalTime): String {
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale("ru"))
        return time.format(timeFormat)
    }
}