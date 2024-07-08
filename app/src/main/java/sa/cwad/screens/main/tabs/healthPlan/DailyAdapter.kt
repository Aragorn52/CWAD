package sa.cwad.screens.main.tabs.healthPlan

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
        return DailyViewHolder(binding)
    }

    override fun getItemCount(): Int = 30

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {

        holder.bind(position)
    }

    inner class DailyViewHolder(
        val binding: DailyCellBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(positionNew: Int) {
            binding.hourListView.adapter = HourEventAdapter(binding.root.context, hourEvents)
        }
//            if(positionNew != position)
//            binding.hourListView.adapter = hourAdapter
//        }
    }
}