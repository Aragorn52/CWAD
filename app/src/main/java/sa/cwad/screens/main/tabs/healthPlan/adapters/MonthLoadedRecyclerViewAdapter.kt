package sa.cwad.screens.main.tabs.healthPlan.adapters

import HourEventRecyclerViewAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sa.cwad.R
import sa.cwad.databinding.DailyCellBinding
import sa.cwad.databinding.MonthCellBinding
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.models.EventForDate
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class MonthLoadedRecyclerViewAdapter(
    private val datePresenter: DatePresenter,
    var mItemList: List<List<LocalDate?>>,
    val backButtonListener: () -> Unit,
    val nextButtonListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MonthCellBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        if (viewType == VIEW_TYPE_ITEM) {
            return ItemViewHolder(binding)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItemList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }


    private inner class ItemViewHolder(
        val binding: MonthCellBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val date = mItemList[position]!!

            binding.monthYearTV.text = datePresenter.monthYearFromDate(date.first()!!)
            val daysInMonth = mItemList[position]

            val calendarAdapter = CalendarAdapter(date.first()!!, daysInMonth, {position, date ->  })
            val layoutManager = GridLayoutManager(binding.root.context, 7)
            binding.calendarRecyclerView.layoutManager = layoutManager
            binding.calendarRecyclerView.adapter = calendarAdapter
            binding.back.setOnClickListener { backButtonListener() }
            binding.next.setOnClickListener { nextButtonListener() }
        }
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(position)
    }
}