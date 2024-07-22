package sa.cwad.screens.main.tabs.healthPlan.viewModels

import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth

class MonthViewModel : ViewModel() {

    var date: LocalDate = LocalDate.now()



    fun daysInMonthList(selectedDate: LocalDate): List<LocalDate?> {

        val daysInMonthList = mutableListOf<LocalDate?>()

        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()

        val prevMonth = selectedDate.minusMonths(1)
        val nextMonth = selectedDate.plusMonths(1)

        val prevYearMonth = YearMonth.from(prevMonth)
        val prevDaysInMonth = prevYearMonth.lengthOfMonth()

        val firstOfMonth = selectedDate.withDayOfMonth(1)
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
                daysInMonthList.add(LocalDate.of(selectedDate.year, selectedDate.month, i - dayOfWeek))
            }
        }

        return daysInMonthList
    }
}