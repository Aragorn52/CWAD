package sa.cwad.screens.main.tabs.healthPlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import sa.cwad.R
import sa.cwad.databinding.FragmentDailyBinding
import sa.cwad.screens.main.tabs.healthPlan.CalendarUtils.Companion.selectedDate
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent
import java.time.LocalDate
import java.time.LocalTime


class DailyFragment : Fragment(R.layout.fragment_daily) {
    private lateinit var selectedDate: LocalDate

//    private val viewModel by viewModelCreator { CalendarViewModel() }

    private lateinit var binding: FragmentDailyBinding
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDailyBinding.inflate(layoutInflater)
//        setDayView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = LocalDate.now()
//        nextDayAction()
//        previousDayAction()
        setHourAdapter()
//        setHourAdapter()
        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_eventEditFragment)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        setDayView()
//        setHourAdapter()
//    }

//    private fun setDayView() {
////        binding.monthDayTV.text = CalendarUtils.monthDayFromDate(selectedDate)
////        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
////        binding.dayOfWeekTV.text = dayOfWeek
//        setHourAdapter()
//    }

//    private fun previousDayAction() {
//        binding.back.setOnClickListener {
//            selectedDate = selectedDate.minusDays(1)
//            setDayView()
//    }

    private fun nextDayAction() {
//        binding.next.setOnClickListener {
//            selectedDate = selectedDate.plusDays(1)
//            setDayView()
//        }
    }

//    override fun invoke(position: Int, date: LocalDate?) {
//        selectedDate = date!!
//        setDayView()
//    }

    private fun setHourAdapter() {
//        binding.monthYearTV.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
//        val daysInMonth = CalendarUtils.daysInMonthList()

//        val adapter = HourEventAdapter(requireContext(), hourEventsList())
//        binding.hourListView.adapter = adapter

//        val list = mutableListOf(
//            HourEvent(
//                time = LocalTime.now(),
//                events = mutableListOf()
//            )
//        )
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        layoutManager.scrollToPositionWithOffset(15, 0);
        val snapHelper: SnapHelper = PagerSnapHelper()

        binding.recyclerView.apply {
            layoutManager.scrollToPositionWithOffset(15, 0);
            val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            val snapHelper: SnapHelper = PagerSnapHelper()
            setLayoutManager(layoutManager)
            adapter = calendarAdapter
            onFlingListener = null
            snapHelper.attachToRecyclerView(binding.recyclerView)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx > 0) {
                    selectedDate.plusDays(1)
                    println("Прокрутка вправо")
                } else if (dx < 0) {
                    selectedDate.minusDays(1)
                    println("Прокрутка влево")
                }
            }
        })
    }

    private fun hourEventsListForDate(date: LocalDate): List<HourEvent> {

        val list = mutableListOf<HourEvent>()
        for (i in 0..23) {
            val time = LocalTime.of(i, 0)
            val events = Event.eventsForDateAndTime(date, time)
            val hourEvent = HourEvent(time, events)
            list.add(hourEvent)
        }
        return list
    }
}