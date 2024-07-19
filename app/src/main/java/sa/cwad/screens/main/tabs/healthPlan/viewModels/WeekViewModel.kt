package sa.cwad.screens.main.tabs.healthPlan.viewModels

import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.time.LocalDate

class WeekViewModel : ViewModel() {

    var date: LocalDate = LocalDate.now()

    fun daysInWeekList(): List<LocalDate?> {
        val days = mutableListOf<LocalDate?>()
        var current = mondayForDate(date)
        val endDate = current?.plusWeeks(1)
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    private fun mondayForDate(currentInit: LocalDate): LocalDate? {
        var current = currentInit
        val oneWeekAgo = current.minusWeeks(1)
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.MONDAY) {
                return current
            }
            current = current.minusDays(1)
        }
        return null
    }
}