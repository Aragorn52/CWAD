package sa.cwad.screens.main.tabs.healthPlan

import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth

class MonthViewModel : ViewModel() {

    var date: LocalDate = LocalDate.now()

    fun daysInMonthList(): List<LocalDate?> {

        val daysInMonthList = mutableListOf<LocalDate?>()

        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val prevMonth = date.minusMonths(1)
        val nextMonth = date.plusMonths(1)

        val prevYearMonth = YearMonth.from(prevMonth)
        val prevDaysInMonth = prevYearMonth.lengthOfMonth()

        val firstOfMonth = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 2..43) {
            if (i <= dayOfWeek) {
                daysInMonthList.add(
                    LocalDate.of(
                        prevMonth.year,
                        prevMonth.month,
                        prevDaysInMonth + i - dayOfWeek
                    )
                )
            } else if (i > daysInMonth + dayOfWeek) {
                daysInMonthList.add(
                    LocalDate.of(
                        nextMonth.year,
                        nextMonth.month,
                        i - dayOfWeek - daysInMonth
                    )
                )
            } else {
                daysInMonthList.add(LocalDate.of(date.year, date.month, i - dayOfWeek))
            }
        }

        return daysInMonthList
    }
}