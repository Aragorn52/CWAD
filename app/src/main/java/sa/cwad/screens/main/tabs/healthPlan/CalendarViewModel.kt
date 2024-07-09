package sa.cwad.screens.main.tabs.healthPlan

import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalendarViewModel() : ViewModel() {

    fun daysInMonthList(date: LocalDate): List<String> {

        val daysInMonthList = mutableListOf<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 2..43) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthList.add("")
            } else {
                val day = i - dayOfWeek
                daysInMonthList.add(day.toString())
            }
        }
        if (daysInMonthList[6] == "") {
            daysInMonthList.removeIf { it == "" }
        }

        return daysInMonthList
    }

    fun monthYearFromDate(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("MMM yyyy", Locale("ru"))
        return dateFormat.format(date)
    }
}