package sa.cwad.screens.main.tabs.healthPlan

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import sa.cwad.R
import java.time.LocalTime

class HourEventAdapter(
    context: Context, hourEvents: List<HourEvent>
) : ArrayAdapter<HourEvent>(context, 0, hourEvents) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event = getItem(position)

        var convertViewNew = convertView
        if (convertViewNew == null) {
            convertViewNew =
                LayoutInflater.from(context).inflate(R.layout.hour_cell, parent, false)
        }

        setHour(convertViewNew!!, event!!.time)
        setEvents(convertViewNew, event.events)

        return convertViewNew
    }

    private fun setHour(convertViewNew: View, time: LocalTime) {
        val timeTv = convertViewNew.findViewById<TextView>(R.id.timeTV)
        timeTv.text = CalendarUtils.formattedShortTime(time)
    }

    private fun setEvents(convertViewNew: View, events: List<Event>) {
        val event1 = convertViewNew.findViewById<TextView>(R.id.event1)
        val event2 = convertViewNew.findViewById<TextView>(R.id.event2)
        val event3 = convertViewNew.findViewById<TextView>(R.id.event3)

        if (events.isEmpty()) {
            hideEvents(event1)
            hideEvents(event2)
            hideEvents(event3)
        } else if (events.size == 1) {
            setEvent(event1, events[0])
            hideEvents(event2)
            hideEvents(event3)
        } else if (events.size == 2) {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            hideEvents(event3)
        } else if (events.size == 3) {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            setEvent(event3, events[2])
        } else {
            setEvent(event1, events[0])
            setEvent(event2, events[1])
            event3.visibility = TextView.VISIBLE
            val eventsNotShown = ((events.size - 2).toString()) + " More events"
            event3.text = eventsNotShown
        }
    }

    private fun setEvent(textView: TextView, event: Event) {
        textView.text = event.name
        textView.visibility = TextView.VISIBLE
    }

    private fun hideEvents(tv: TextView) {
        tv.visibility = View.INVISIBLE
    }
}