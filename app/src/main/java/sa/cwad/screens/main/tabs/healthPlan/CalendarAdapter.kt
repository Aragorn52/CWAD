package sa.cwad.screens.main.tabs.healthPlan

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.databinding.CalendarCellBinding
import java.time.LocalDate


typealias OnItemListener = (position: Int, date: LocalDate?) -> Unit

class CalendarAdapter(
    private val selectedDate: LocalDate,
    private val days: List<LocalDate?>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val layoutParams = binding.root.layoutParams
        if (days.size > 15) {
            layoutParams.height = (parent.height * 0.166666666).toInt()
        } else {
            layoutParams.height = parent.height
        }
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

        holder.bind(days[position])
    }

    inner class CalendarViewHolder(
        binding: CalendarCellBinding,
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {
        private val day: TextView = binding.cellDayText
        private val patentView = binding.parentView
        fun bind(date: LocalDate?) {
            day.text = date!!.dayOfMonth.toString()
            if (date == selectedDate) {
                patentView.setBackgroundColor(Color.LTGRAY)
            }
            if (date.month == selectedDate.month) {
                day.setTextColor(Color.BLACK)
            } else {
                day.setTextColor(Color.LTGRAY)
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onItemListener.invoke(adapterPosition, days[adapterPosition])
        }

    }
}