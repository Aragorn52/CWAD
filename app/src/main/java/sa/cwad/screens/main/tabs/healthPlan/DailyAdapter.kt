package sa.cwad.screens.main.tabs.healthPlan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.databinding.DailyCellBinding
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent

class DailyAdapter(
    private val hourEvents: List<HourEvent>,
) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = DailyCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
//        val layoutParams = binding.root.layoutParams
//        if (days.size > 15) {
//            layoutParams.height = (parent.height * 0.166666666).toInt()
//        } else {
//            layoutParams.height = parent.height
//        }
        return DailyViewHolder(binding)
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun replay(item: List<HourEvent>) {
//        hourEvents = item
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int = 30

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {

        holder.bind()
    }

    inner class DailyViewHolder(
        val binding: DailyCellBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val adapter = HourEventAdapter(binding.root.context, hourEvents)
            binding.hourListView.adapter = adapter
//            day.text = date!!.dayOfMonth.toString()
//            if (date == CalendarUtils.selectedDate) {
//                patentView.setBackgroundColor(Color.LTGRAY)
//            }
//            if (date.month == CalendarUtils.selectedDate.month) {
//                day.setTextColor(Color.BLACK)
//            } else {
//                day.setTextColor(Color.LTGRAY)
//            }
//            itemView.setOnClickListener(this)
        }

//        override fun onClick(v: View) {
//            onItemListener.invoke(adapterPosition, days[adapterPosition])
//        }

    }
}