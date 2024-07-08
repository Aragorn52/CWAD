package sa.cwad.screens.main.tabs.healthPlan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.databinding.DailyCellBinding
import sa.cwad.screens.main.tabs.healthPlan.models.HourEvent

class DailyAdapter(
    private var hourEvents: List<HourEvent>,
) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = DailyCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DailyViewHolder(binding)
    }

    override fun getItemCount(): Int = 30

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(list: List<HourEvent>) {
        hourEvents = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {

        holder.bind()
    }

    inner class DailyViewHolder(
        val binding: DailyCellBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.hourListView.adapter = HourEventAdapter(binding.root.context, hourEvents)
        }
    }
}