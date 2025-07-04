package sa.cwad.screens.main.tabs.healthPlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import sa.cwad.R
import sa.cwad.databinding.FragmentWeekBinding
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import java.time.LocalDate

class WeekFragment : Fragment(R.layout.fragment_week), OnItemListener {

//    private val viewModel by viewModelCreator { CalendarViewModel() }

    private lateinit var binding: FragmentWeekBinding
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWeekBinding.inflate(layoutInflater)
        setWeekView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CalendarUtils.selectedDate = LocalDate.now()
        nextWeekAction()
        previousWeekAction()
        setEventAdapter()
        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_weekFragment_to_eventEditFragment)
        }
        binding.dailyBNT.setOnClickListener {
            findNavController().navigate(R.id.action_weekFragment_to_dailyFragment)
        }
    }

    override fun onResume() {
        setEventAdapter()
        super.onResume()
    }

    private fun setWeekView() {
        binding.monthYearTV.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        val days = CalendarUtils.daysInWeekList()

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.adapter = calendarAdapter
        binding.calendarRecyclerView.layoutManager = layoutManager
    }

    private fun previousWeekAction() {
        binding.back.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
            setWeekView()
        }
    }

    private fun nextWeekAction() {
        binding.next.setOnClickListener {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
            setWeekView()
        }
    }

    override fun invoke(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date!!
        setWeekView()
    }

    private fun setEventAdapter() {
        val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
        val eventAdapter = EventAdapter(requireContext(), dailyEvents)
        binding.eventsListView.adapter = eventAdapter
    }
}