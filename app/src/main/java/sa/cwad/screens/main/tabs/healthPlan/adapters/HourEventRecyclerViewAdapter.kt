import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.R
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import sa.cwad.screens.main.tabs.healthPlan.models.entities.HourEvent
import java.time.LocalTime

class HourEventRecyclerViewAdapter(
    private val datePresenter: DatePresenter,
    private val eventsSelectedDay: List<Event?>
) : RecyclerView.Adapter<HourEventRecyclerViewAdapter.HourEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourEventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_cell, parent, false)
        return HourEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: HourEventViewHolder, position: Int) {
        val event = eventsSelectedDay[position]
        holder.bind(event!!, datePresenter)
    }


    override fun getItemCount(): Int = eventsSelectedDay.size

   inner class HourEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTV)
        private val event1TextView: TextView = itemView.findViewById(R.id.event1)
        private val event2TextView: TextView = itemView.findViewById(R.id.event2)
        private val event3TextView: TextView = itemView.findViewById(R.id.event3)

        fun bind(event: Event, datePresenter: DatePresenter) {
            setHour(event.time, datePresenter)
            setEvents(listOf(event))
        }

        private fun setHour(time: LocalTime, datePresenter: DatePresenter) {
            timeTextView.text = datePresenter.formattedShortTime(time)
        }

       private fun setEvent(textView: TextView, event: Event) {
           textView.text = event.name
           textView.visibility = View.VISIBLE
       }

       private fun hideEvents(vararg textViews: TextView) {
           textViews.forEach { it.visibility = View.INVISIBLE }
       }

        private fun setEvents(events: List<Event>) {

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
        }
    }
