package sa.cwad.screens.main.tabs.healthPlan

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.databinding.CalendarCellBinding


//typealias OnItemListener = (position: Int, dayText: String) -> Unit
interface OnItemListener {
    fun onItemClick(position: Int, dayText: String): Unit
}

class CalendarAdapter(
    private val dayOfMonth: List<String>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val layoutParams = binding.root.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = dayOfMonth.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = dayOfMonth[position]
        holder.bind()
    }

    inner class CalendarViewHolder(
        binding: CalendarCellBinding,
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {
        val dayOfMonth: TextView = binding.cellDayText

        fun bind() {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text.toString())
        }

    }
}