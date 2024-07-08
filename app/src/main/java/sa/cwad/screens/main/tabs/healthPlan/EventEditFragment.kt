package sa.cwad.screens.main.tabs.healthPlan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sa.cwad.R
import sa.cwad.databinding.EventEditFragmentBinding
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import java.time.LocalTime
class EventEditFragment : Fragment(R.layout.event_edit_fragment) {

    //    private val viewModel by viewModelCreator { CalendarViewModel() }
    private var time: LocalTime = LocalTime.now()

    private lateinit var binding: EventEditFragmentBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EventEditFragmentBinding.bind(view)
        time = LocalTime.now()
        binding.eventDateTV.text = "Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate)
        binding.eventTimeTV.text = "Time: " + CalendarUtils.formattedTime(time = time)
        saveEvent()

    }

    private fun saveEvent() {
        binding.save.setOnClickListener {
            val eventName = binding.eventNameET.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate, time)
            Event.eventsList.add(newEvent)
            findNavController().navigate(R.id.action_eventEditFragment_to_dailyFragment)
        }
    }
}