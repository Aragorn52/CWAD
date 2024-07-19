package sa.cwad.screens.main.tabs.healthPlan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import sa.cwad.R
import sa.cwad.databinding.FragmentWeekBinding
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.EventService
import sa.cwad.screens.main.tabs.healthPlan.viewModels.WeekViewModel
import sa.cwad.screens.main.tabs.healthPlan.adapters.CalendarAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.EventAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.OnItemListener
import sa.cwad.utils.viewModelCreator
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class WeekFragment : Fragment(R.layout.fragment_week), OnItemListener {

    private val viewModel by viewModelCreator { WeekViewModel() }

    private lateinit var binding: FragmentWeekBinding
    private var firstSelectTime: Long = 0
    private val doubleClickTime = 500
    private var formattedDate: String? = null

    @Inject
    lateinit var datePresenter: DatePresenter

    @Inject
    lateinit var eventService: EventService

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
        binding.monthYearTV.text = datePresenter.monthYearFromDate(viewModel.date)
        val days = viewModel.daysInWeekList()

        val calendarAdapter = CalendarAdapter(viewModel.date, days, this)
        val layoutManager = GridLayoutManager(requireContext(), 7)
        binding.calendarRecyclerView.adapter = calendarAdapter
        binding.calendarRecyclerView.layoutManager = layoutManager
    }

    private fun previousWeekAction() {
        binding.back.setOnClickListener {
            viewModel.date = viewModel.date.minusWeeks(1)
            setWeekView()
        }
    }

    private fun nextWeekAction() {
        binding.next.setOnClickListener {
            viewModel.date = viewModel.date.plusWeeks(1)
            setWeekView()
        }
    }

    override fun invoke(position: Int, date: LocalDate?) {
        viewModel.date = date!!
        setWeekView()
    }

    private fun setEventAdapter() {
        val dailyEvents = eventService.eventsForDate(viewModel.date)
        val eventAdapter = EventAdapter(datePresenter, requireContext(), dailyEvents)
        binding.eventsListView.adapter = eventAdapter
    }
}