package sa.cwad.screens.main.tabs.healthPlan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import sa.cwad.R
import sa.cwad.databinding.FragmentDailyBinding
import sa.cwad.recyclerView.decorators.HorizontalSpaceItemDecoration
import sa.cwad.recyclerView.listeners.DiagonalBlockerTouchListener
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.adapters.DailyLoadedRecyclerViewAdapter
import sa.cwad.screens.main.tabs.healthPlan.models.entities.EventForDate
import sa.cwad.screens.main.tabs.healthPlan.viewModels.DailyViewModel
import java.time.format.DateTimeFormatter
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
//        initEventData()
        initAdapter()
        initListeners()

        binding.newEventBT.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val direction = DailyFragmentDirections.actionDailyFragmentToEventEditFragment(
                viewModel.date.format(formatter)
            )
            findNavController().navigate(direction)
        }
    }

    private suspend fun initEventData() {
        lifecycleScope.launch {
            var date = viewModel.date.minusDays(1)
            for (i in 0 until 10) {
                val events = viewModel.getEventList(date)
                val element = EventForDate(
                    date,
                    events
                )
                rowsArrayList.add(element)
                date = date.plusDays(1)
            }
        }
    }


    private fun initAdapter() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        lifecycleScope.launch {
            initEventData()
            binding.recyclerView.adapter =
                DailyLoadedRecyclerViewAdapter(
                    datePresenter,
                    rowsArrayList,
                    ::goBackButton,
                    ::goNextButton
                )
        }
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
                val firstVisibleItemPosition =
                    layoutManager?.findFirstCompletelyVisibleItemPosition()

                if (lastVisibleItemPosition != null && lastVisibleItemPosition != -1) {
                    viewModel.date = rowsArrayList[lastVisibleItemPosition]?.date ?: viewModel.date
                }

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
        binding.recyclerView.post {
            binding.recyclerView.smoothScrollToPosition(lastVisibleItemPosition - 1)
        }
    }

    private fun goNextButton() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager?
        val pos = layoutManager!!.findFirstVisibleItemPosition()
        binding.recyclerView.post {
            binding.recyclerView.smoothScrollToPosition(pos + 1)
        }
    }


    private fun loadUpMore() {
        for (i in 0 until 10) {
            val dateLast = rowsArrayList.last()!!.date
            var actualDate = dateLast.plusDays(1)
            lifecycleScope.launch {
                val events = viewModel.getEventList(actualDate)
                val element = EventForDate(
                    actualDate,
                    events
                )
                rowsArrayList.add(element)
                actualDate = actualDate.plusDays(1)
            }
        }

        notifyAdapter()
    }

    private fun loadDownMore() {
        lifecycleScope.launch {
            val dateFirst = rowsArrayList[0]!!.date
            val lastDay = dateFirst.minusDays(1)
            val events = viewModel.getEventList(lastDay)
            val element = EventForDate(
                lastDay,
                events
            )
            rowsArrayList.add(index = 0, element = element)
        }

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