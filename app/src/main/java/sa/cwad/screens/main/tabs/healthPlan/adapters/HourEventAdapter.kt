import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.R
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.models.Event
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent
import java.time.LocalTime

class HourEventRecyclerViewAdapter(
    private val datePresenter: DatePresenter,
    private val hourEvents: List<HourEvent>
) : RecyclerView.Adapter<HourEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourEventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_cell, parent, false)
        return HourEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourEventViewHolder, position: Int) {
        val t = hourEvents
        val event = hourEvents[position]
        bind(holder, event)
    }

    override fun getItemCount(): Int = hourEvents.size

    private fun bind(holder: HourEventViewHolder, event: HourEvent) {
        val t1 = event
        setHour(holder, event.time)
        setEvents(holder, event.events)
    }

    private fun setHour(viewHolder: HourEventViewHolder, time: LocalTime) {
        viewHolder.timeTextView.text = datePresenter.formattedShortTime(time)
    }

    private fun setEvents(holder: HourEventViewHolder, events: List<Event>) {
        val t = events
        val event1TextView = holder.event1TextView
        val event2TextView = holder.event2TextView
        val event3TextView = holder.event3TextView

        when (events.size) {
            0 -> hideEvents(event1TextView, event2TextView, event3TextView)
            1 -> {
                setEvent(event1TextView, events[0])
                hideEvents(event2TextView, event3TextView)
            }
            2 -> {
                setEvent(event1TextView, events[0])
                setEvent(event2TextView, events[1])
                hideEvents(event3TextView)
            }
            3 -> {
                setEvent(event1TextView, events[0])
                setEvent(event2TextView, events[1])
                setEvent(event3TextView, events[2])
            }
            else -> {
                setEvent(event1TextView, events[0])
                setEvent(event2TextView, events[1])
                event3TextView.visibility = View.VISIBLE
                val eventsNotShown = "${events.size - 2} More events"
                event3TextView.text = eventsNotShown
            }
        }
    }

    private fun setEvent(textView: TextView, event: Event) {
        textView.text = event.name
        textView.visibility = View.VISIBLE
    }

    private fun hideEvents(vararg textViews: TextView) {
        textViews.forEach { it.visibility = View.INVISIBLE }
    }
}

class HourEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val timeTextView: TextView = itemView.findViewById(R.id.timeTV)
    val event1TextView: TextView = itemView.findViewById(R.id.event1)
    val event2TextView: TextView = itemView.findViewById(R.id.event2)
    val event3TextView: TextView = itemView.findViewById(R.id.event3)
}
