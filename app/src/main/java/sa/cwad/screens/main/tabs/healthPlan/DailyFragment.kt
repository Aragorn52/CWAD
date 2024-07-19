package sa.cwad.screens.main.tabs.healthPlan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.cwad.R
import sa.cwad.databinding.FragmentDailyBinding
import sa.cwad.decorators.HorizontalSpaceItemDecoration
import sa.cwad.screens.main.tabs.healthPlan.adapters.DailyLoadedRecyclerViewAdapter
import sa.cwad.screens.main.tabs.healthPlan.models.EventForDate
import javax.inject.Inject


@AndroidEntryPoint
class DailyFragment : Fragment(R.layout.fragment_daily) {

    @Inject
    lateinit var datePresenter: DatePresenter

    private val viewModel by viewModels<DailyViewModel>()

    private lateinit var binding: FragmentDailyBinding

    private val rowsArrayList = arrayListOf<EventForDate?>()

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDailyBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventData()
        initAdapter()
        initListeners()

        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_eventEditFragment)
        }
    }

    private fun initEventData() {
        var date = viewModel.date.minusDays(1)
        for (i in 0 until 10) {

            val element = EventForDate(
                date,
                viewModel.hourEventsListForDate(date)
            )
            rowsArrayList.add(element)
            date = date.plusDays(1)
            viewModel.date = date
        }
    }

    private fun initAdapter() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter =
            DailyLoadedRecyclerViewAdapter(datePresenter, rowsArrayList, ::goBackButton, ::goNextButton)
        binding.recyclerView.layoutManager = manager

        val snapHelper: SnapHelper = PagerSnapHelper()
        binding.recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.addItemDecoration(HorizontalSpaceItemDecoration(16))
        manager.scrollToPositionWithOffset(1, 0)
    }

    private fun initListeners() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = layoutManager?.findLastCompletelyVisibleItemPosition()
                val firstVisibleItemPosition = layoutManager?.findFirstCompletelyVisibleItemPosition()

                if (!isLoading && (lastVisibleItemPosition == rowsArrayList.size - 1)) {
                    loadUpMore()
                    isLoading = true
                }
                if (!isLoading && (firstVisibleItemPosition == 0)) {
                    loadDownMore()
                    isLoading = true
                }
            }
        })
        binding.recyclerView.addOnItemTouchListener(DiagonalBlockerTouchListener(true, 150F))
    }

    private fun goBackButton() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager?
        val lastVisibleItemPosition = layoutManager!!.findLastCompletelyVisibleItemPosition()
        if (lastVisibleItemPosition == 0) {
            loadDownMore()
        }
        binding.recyclerView.post{
            binding.recyclerView.smoothScrollToPosition(lastVisibleItemPosition - 1)
        }
    }

    private fun goNextButton() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager?
        val pos = layoutManager!!.findFirstVisibleItemPosition()
        binding.recyclerView.post{
            binding.recyclerView.smoothScrollToPosition(pos + 1)
        }
    }

    private fun loadUpMore() {
        for (i in 0 until 10) {
            val dateLast = rowsArrayList.last()!!.date
            val actualDate = dateLast.plusDays(1)
            val event = EventForDate(actualDate, viewModel.hourEventsListForDate(actualDate))
            rowsArrayList.add(event)
        }

        notifyAdapter()
    }

    private fun loadDownMore() {
        val dateFirst = rowsArrayList[0]!!.date
        val lastDay = dateFirst.minusDays(1)
        val events = viewModel.hourEventsListForDate(lastDay)
        rowsArrayList.add(index = 0, element = EventForDate(lastDay, events))

        binding.recyclerView.adapter?.notifyItemInserted(0)
        notifyAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.recyclerView.adapter?.notifyDataSetChanged()
            isLoading = false
        }
    }
}