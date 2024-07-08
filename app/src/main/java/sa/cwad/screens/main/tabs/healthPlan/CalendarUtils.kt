package sa.cwad.screens.main.tabs.healthPlan

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalendarUtils {
    companion object {
        lateinit var selectedDate: LocalDate

        fun daysInMonthList(date: LocalDate): MutableList<LocalDate?> {

            val daysInMonthList = mutableListOf<LocalDate?>()
            val yearMonth = YearMonth.from(date)
            val daysInMonth = yearMonth.lengthOfMonth()
            val firstOfMonth = date.withDayOfMonth(1)
            val dayOfWeek = firstOfMonth.dayOfWeek.value
            for (i in 2..43) {
                if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                    daysInMonthList.add(null)
                } else {
                    val day = i - dayOfWeek
                    daysInMonthList.add(LocalDate.of(selectedDate.year, selectedDate.month, day))
                }
            }
            if (daysInMonthList[6] == null) {
                daysInMonthList.removeIf { it == null }
            }

            return daysInMonthList
        }

        fun daysInWeekList(date: LocalDate): List<LocalDate?> {
            val days = mutableListOf<LocalDate?>()
            var current = sundayForDate(selectedDate)
            val endDate = current?.plusWeeks(1)
                while (current!!.isBefore(endDate)) {
                    days.add(current)
                    current = current.plusDays(1)
                }
            return days
        }

        private fun sundayForDate(currentInit: LocalDate): LocalDate? {
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
}