package sa.cwad.screens.main.tabs.healthPlan

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.cwad.decorators.HorizontalSpaceItemDecoration
import javax.inject.Inject
import javax.inject.Singleton

typealias LoadData = () -> Unit

@Singleton
open class BaseConfigurationCalendarCreator<T : RecyclerView.ViewHolder> @Inject constructor() {

    private var isLoading = false

    fun initAdapter(
        context: Context,
        recyclerView: RecyclerView,
        recyclerViewAdapter: RecyclerView.Adapter<T>
    ) {
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = manager

        val snapHelper: SnapHelper = PagerSnapHelper()
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(HorizontalSpaceItemDecoration(16))
        manager.scrollToPositionWithOffset(100, 0)
    }

    fun initScrollListener(
        recyclerView: RecyclerView,
        recyclerViewAdapter: RecyclerView.Adapter<T>,
        leftScrollLoadDataListener: LoadData,
        rightScrollLoadDataListener: LoadData,
        lastElement: Int,
        firstElement: Int
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = layoutManager?.findLastCompletelyVisibleItemPosition()
                val firstVisibleItemPosition =
                    layoutManager?.findFirstCompletelyVisibleItemPosition()

                if (!isLoading && (lastVisibleItemPosition == lastElement)) {
                    loadingData(recyclerViewAdapter, leftScrollLoadDataListener)
                    isLoading = true
                }
                if (!isLoading && (firstVisibleItemPosition == firstElement)) {
                    loadingData(recyclerViewAdapter, rightScrollLoadDataListener)
                    isLoading = true
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadingData(
        recyclerViewAdapter: RecyclerView.Adapter<T>,
        callback: LoadData
    ) {
        callback()
        CoroutineScope(Dispatchers.Main).launch {
            recyclerViewAdapter.notifyDataSetChanged()
            isLoading = false
        }
    }
}