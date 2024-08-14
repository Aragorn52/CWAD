package sa.cwad.screens.main.tabs.healthPlan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import sa.cwad.R
import sa.cwad.databinding.EventEditFragmentBinding
import sa.cwad.screens.main.tabs.dashboard.BoxFragmentArgs
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.EventService
import sa.cwad.screens.main.tabs.healthPlan.viewModels.EventEditViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@AndroidEntryPoint
class EventEditFragment : Fragment(R.layout.event_edit_fragment) {

    @Inject
    lateinit var datePresenter: DatePresenter

    @Inject
    lateinit var eventService: EventService

    private val viewModel by viewModels<EventEditViewModel>()

    private var time: LocalTime = LocalTime.now()

    private val args by navArgs<EventEditFragmentArgs>()

    private lateinit var binding: EventEditFragmentBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val selectedDate = LocalDate.parse(args.selectedDate, formatter)
        viewModel.date = selectedDate
        binding = EventEditFragmentBinding.bind(view)
        time = LocalTime.now()
        binding.eventDateTV.text = "Date: " + datePresenter.formattedDate(viewModel.date)
        binding.eventTimeTV.text = "Time: " + datePresenter.formattedTime(time = time)
        saveEvent()
    }

    private fun saveEvent() {
        binding.save.setOnClickListener {
            val eventName = binding.eventNameET.text.toString()
            viewModel.saveEvent(eventName, viewModel.date, time)
            findNavController().navigate(R.id.action_eventEditFragment_to_dailyFragment)
        }
    }
}