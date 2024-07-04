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


typealias OnItemListener = (position: Int, dayText: String) -> Unit

class CalendarAdapter(
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        if (date == null) {
            holder.dayOfMonth.text = ""
        } else {
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if (date == CalendarUtils.selectedDate) {
                holder.patentView.setBackgroundColor(Color.LTGRAY)
            } else {
                holder.patentView.setBackgroundColor(Color.WHITE)
            }
        }
        holder.bind()
    }

    inner class CalendarViewHolder(
        binding: CalendarCellBinding,
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {
        val dayOfMonth: TextView = binding.cellDayText
        val patentView = binding.parentView

        fun bind() {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onItemListener.invoke(adapterPosition, dayOfMonth.text.toString())
        }

    }
}