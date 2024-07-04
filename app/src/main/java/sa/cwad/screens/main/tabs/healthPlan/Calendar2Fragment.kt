package sa.cwad.screens.main.tabs.healthPlan

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import sa.cwad.R
import sa.cwad.databinding.Calendar2Binding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class Calendar2Fragment : Fragment(R.layout.calendar2), OnItemListener {

    private lateinit var binding: Calendar2Binding
    private lateinit var selectedDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = Calendar2Binding.bind(view)
        selectedDate = LocalDate.now()
        setMonthView()
        binding.backMonth.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }
        binding.nextMonth.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        binding.monthYearTV.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthList(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthList(date: LocalDate): List<String> {

        val daysInMonthList = mutableListOf<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
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
    private fun monthYearFromDate(date: LocalDate): String {
        val dateFormat = DateTimeFormatter.ofPattern("MMM yyyy", Locale("ru"))
        val t = dateFormat.format(date)
        return dateFormat.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun previousMonthAction(view: View) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextMonthAction(view: View) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun invoke(position: Int, dayText: String) {
        if (dayText != "") {
            val message = "Selected day " + dayText + " " + monthYearFromDate(selectedDate)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}