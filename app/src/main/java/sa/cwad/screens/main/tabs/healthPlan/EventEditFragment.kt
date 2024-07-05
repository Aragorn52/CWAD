package sa.cwad.screens.main.tabs.healthPlan

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sa.cwad.R
import sa.cwad.databinding.EventEditFragmentBinding
import java.time.LocalTime
class EventEditFragment : Fragment(R.layout.event_edit_fragment) {

    //    private val viewModel by viewModelCreator { CalendarViewModel() }
    @RequiresApi(Build.VERSION_CODES.O)
    private var time: LocalTime = LocalTime.now()

    private lateinit var binding: EventEditFragmentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EventEditFragmentBinding.bind(view)
        time = LocalTime.now()
        binding.eventDateTV.text = "Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate)
        binding.eventTimeTV.text = "Time: " + CalendarUtils.formattedTime(time = time)
        saveEvent()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveEvent() {
        binding.save.setOnClickListener {
            val eventName = binding.eventNameET.text.toString()
            val newEvent = Event(eventName, CalendarUtils.selectedDate, time)
            Event.eventsList.add(newEvent)
            findNavController().popBackStack()
        }
    }
}