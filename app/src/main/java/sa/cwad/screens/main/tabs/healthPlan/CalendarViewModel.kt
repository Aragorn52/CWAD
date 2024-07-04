package sa.cwad.screens.main.tabs.healthPlan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sa.model.EmptyFieldException
import sa.model.accounts.AccountsRepository
import sa.cwad.utils.MutableLiveEvent
import sa.cwad.utils.MutableUnitLiveEvent
import sa.cwad.utils.publishEvent
import sa.cwad.utils.share
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class CalendarViewModel() : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthYearFromDate(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("MMM yyyy", Locale("ru"))
        return dateFormat.format(date)
    }
}