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
import sa.cwad.databinding.WeekFragmentBinding
import sa.cwad.utils.viewModelCreator
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class WeekFragment : Fragment(R.layout.week_fragment), OnItemListener {

//    private val viewModel by viewModelCreator { CalendarViewModel() }

    private lateinit var binding: WeekFragmentBinding
    private lateinit var selectedDate: LocalDate
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WeekFragmentBinding.bind(view)
        selectedDate = LocalDate.now()
        setWeekView()
        previousMonthAction()
        nextMonthAction()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWeekView() {
        binding.monthYearTV.text = CalendarUtils.monthYearFromDate(selectedDate)
        val days = CalendarUtils.daysInWeekList(selectedDate)

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun previousMonthAction() {
        binding.backMonth.setOnClickListener {
            selectedDate = selectedDate.minusWeeks(1)
            setWeekView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextMonthAction() {
        binding.nextMonth.setOnClickListener {
            selectedDate = selectedDate.plusWeeks(1)
            setWeekView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun invoke(position: Int, dayText: String) {
        val date = dayText + " " + CalendarUtils.monthYearFromDate(selectedDate)
        if (dayText != "") {
            when {
                firstSelectTime == 0L -> {
                    // Первый выбор даты
                    firstSelectTime = System.currentTimeMillis()
                    formattedDate = date
                }

                (System.currentTimeMillis() - firstSelectTime <= doubleClickTime) && (formattedDate == date) -> {
                    // Время между двумя выборами меньше заданной задержки, считаем это двойным кликом
                    val message = "Selected day " + dayText + " " + CalendarUtils.monthYearFromDate(selectedDate)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    // Сброс времени первого выбора, чтобы готовиться к следующему двойному клику
                    firstSelectTime = 0
                    formattedDate = date
                }

                else -> {
                    // Сброс времени первого выбора, если это не был двойной клик
                    firstSelectTime = 0
                    formattedDate = date
                }
            }
        }
    }
}