package sa.cwad.screens.main.tabs.healthPlan.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import sa.cwad.R
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.models.Event

class EventAdapter(
    private val datePresenter: DatePresenter, context: Context, events: List<Event>
) : ArrayAdapter<Event>(context, 0, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event = getItem(position)

        var convertViewNew = convertView
        if (convertViewNew == null) {
            convertViewNew =
                LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        }
        val eventCellTV = convertViewNew?.findViewById<TextView>(R.id.eventCellTV)
        val eventTitle = event?.name + " " + datePresenter.formattedTime(event!!.time)
        eventCellTV?.text = eventTitle
        return convertViewNew!!
    }
}