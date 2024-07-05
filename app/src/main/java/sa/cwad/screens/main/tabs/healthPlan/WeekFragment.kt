package sa.cwad.screens.main.tabs.healthPlan

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeekFragmentBinding.inflate(layoutInflater)
        setWeekView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CalendarUtils.selectedDate = LocalDate.now()
        nextWeekAction()
        previousWeekAction()
        setEventAdapter()
        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_weekFragment_to_eventEditFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        setEventAdapter()
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWeekView() {
        binding.monthYearTV.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        val days = CalendarUtils.daysInWeekList(CalendarUtils.selectedDate)

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.adapter = calendarAdapter
        binding.calendarRecyclerView.layoutManager = layoutManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun previousWeekAction() {
        binding.back.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
            setWeekView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextWeekAction() {
        binding.next.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
            setWeekView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun invoke(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date!!
        setWeekView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setEventAdapter() {
        val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
        val eventAdapter = EventAdapter(requireContext(), dailyEvents)
        binding.eventsListView.adapter = eventAdapter
    }
}