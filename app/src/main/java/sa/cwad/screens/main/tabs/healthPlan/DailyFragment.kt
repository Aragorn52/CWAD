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
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale


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
        showDay(selectedDate)

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

    private fun setDayView() {
//        binding.monthDayTV.text = CalendarUtils.monthDayFromDate(selectedDate)
//        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
//        binding.dayOfWeekTV.text = dayOfWeek
        setHourAdapter()
    }

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

    private fun showDay(date: LocalDate) {
        binding.monthDayTV.text = CalendarUtils.monthDayFromDate(date)
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek
    }

    private fun plusDay() {
        selectedDate = selectedDate.plusDays(1)
        binding.monthDayTV.text = CalendarUtils.monthDayFromDate(selectedDate)
        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek
    }

    private fun minusDay() {
        selectedDate = selectedDate.minusDays(1)
        binding.monthDayTV.text = CalendarUtils.monthDayFromDate(selectedDate)
        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek
    }

    private fun setHourAdapter() {

        binding.recyclerView.apply {
            adapter = DailyAdapter(hourEventsListForDate())
            val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            layoutManager = manager

            val snapHelper: SnapHelper = PagerSnapHelper()
            manager.scrollToPositionWithOffset(15, 0)
            onFlingListener = null
            snapHelper.attachToRecyclerView(binding.recyclerView)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var lastVisibleItemPosition = -1

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val currentFirstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (currentFirstVisibleItemPosition == lastVisibleItemPosition + 1) {
                        plusDay()
                        (adapter as DailyAdapter).changeList(hourEventsListForDate())
                    }

                    if (currentFirstVisibleItemPosition == lastVisibleItemPosition - 1) {
                        minusDay()
                        (adapter as DailyAdapter).changeList(hourEventsListForDate())
                    }
                    lastVisibleItemPosition = currentFirstVisibleItemPosition
                }
            })
        }
    }

    private fun hourEventsListForDate(): List<HourEvent> {

        val list = mutableListOf<HourEvent>()
        for (i in 0..23) {
            val time = LocalTime.of(i, 0)
            val events = Event.eventsForDateAndTime(selectedDate, time)
            val hourEvent = HourEvent(time, events)
            list.add(hourEvent)
        }
        return list
    }
}