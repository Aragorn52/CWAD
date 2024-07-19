package sa.cwad.screens.main.tabs.healthPlan.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import sa.cwad.R
import sa.cwad.databinding.FragmentMonthBinding
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.viewModels.MonthViewModel
import sa.cwad.screens.main.tabs.healthPlan.adapters.CalendarAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.OnItemListener
import sa.cwad.utils.viewModelCreator
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MonthFragment : Fragment(R.layout.fragment_month), OnItemListener {

    private val viewModel by viewModelCreator { MonthViewModel() }

    @Inject lateinit var datePresenter: DatePresenter

    private lateinit var binding: FragmentMonthBinding
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMonthBinding.bind(view)
        setMonthView()
        previousMonthAction()
        nextMonthAction()
        binding.weekly.setOnClickListener {
            findNavController().navigate(R.id.action_monthFragment_to_weekFragment)
        }
    }

    private fun setMonthView() {
        binding.monthYearTV.text = datePresenter.monthYearFromDate(viewModel.date)
        val daysInMonth = viewModel.daysInMonthList()

        val calendarAdapter = CalendarAdapter(viewModel.date, daysInMonth, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.layoutManager = layoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    private fun previousMonthAction() {
        binding.backMonth.setOnClickListener {
            viewModel.date = viewModel.date.minusMonths(1)
            setMonthView()
        }
    }

    private fun nextMonthAction() {
        binding.nextMonth.setOnClickListener {
            viewModel.date = viewModel.date.plusMonths(1)
            setMonthView()
        }
    }

    override fun invoke(position: Int, date: LocalDate?) {
        viewModel.date = date!!
//        val date = date + " " + CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        if (date != null) {
            viewModel.date = date
            setMonthView()
            when {
//                firstSelectTime == 0L -> {
//                    // Первый выбор даты
//                    firstSelectTime = System.currentTimeMillis()
//                    formattedDate = date
//                }
//
//                (System.currentTimeMillis() - firstSelectTime <= doubleClickTime) && (formattedDate == date) -> {
//                    // Время между двумя выборами меньше заданной задержки, считаем это двойным кликом
//                    val message = "Selected day " + dayText + " " + CalendarUtils.monthYearFromDate(
//                        CalendarUtils.selectedDate
//                    )
//                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                    // Сброс времени первого выбора, чтобы готовиться к следующему двойному клику
//                    firstSelectTime = 0
//                    formattedDate = date
//                }
//
//                else -> {
//                    // Сброс времени первого выбора, если это не был двойной клик
//                    firstSelectTime = 0
//                    formattedDate = date
//                }
            }
        }
    }
}