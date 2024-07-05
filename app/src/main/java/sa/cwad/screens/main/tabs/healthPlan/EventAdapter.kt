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

class EventAdapter(
    context: Context, events: List<Event>
) : ArrayAdapter<Event>(context, 0, events) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val event = getItem(position)

        var convertViewNew = convertView
        if (convertViewNew == null) {
            convertViewNew =
                LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        }
        val eventCellTV = convertViewNew?.findViewById<TextView>(R.id.eventCellTV)
        val eventTitle = event?.name + " " + CalendarUtils.formattedTime(event!!.time)
        eventCellTV?.text = eventTitle
        return convertViewNew!!
    }
}