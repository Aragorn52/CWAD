package sa.cwad.screens.main.tabs.healthPlan

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sa.cwad.R
import sa.cwad.databinding.DailyFragmentBinding
import sa.cwad.screens.main.tabs.healthPlan.CalendarUtils.Companion.selectedDate
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

class DailyFragment : Fragment(R.layout.daily_fragment), OnItemListener {

//    private val viewModel by viewModelCreator { CalendarViewModel() }

    private lateinit var binding: DailyFragmentBinding
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DailyFragmentBinding.inflate(layoutInflater)
        setDayView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedDate = LocalDate.now()
        nextDayAction()
        previousDayAction()
        setHourAdapter()
        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_eventEditFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        setDayView()
        setHourAdapter()
    }

    private fun setDayView() {
        binding.monthDayTV.text = CalendarUtils.monthDayFromDate(selectedDate)
        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek
        setHourAdapter()
    }

    private fun previousDayAction() {
        binding.back.setOnClickListener {
            selectedDate = selectedDate.minusDays(1)
            setDayView()
        }
    }

    private fun nextDayAction() {
        binding.next.setOnClickListener {
            selectedDate = selectedDate.plusDays(1)
            setDayView()
        }
    }

    override fun invoke(position: Int, date: LocalDate?) {
        selectedDate = date!!
        setDayView()
    }

    private fun setHourAdapter() {
        val adapter = HourEventAdapter(requireContext(), hourEventsList())
        binding.hourListView.adapter = adapter
    }

    private fun hourEventsList(): List<HourEvent> {

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