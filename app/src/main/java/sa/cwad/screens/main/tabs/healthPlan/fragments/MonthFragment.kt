package sa.cwad.screens.main.tabs.healthPlan.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sa.cwad.R
import sa.cwad.databinding.FragmentMonthBinding
import sa.cwad.recyclerView.decorators.HorizontalSpaceItemDecoration
import sa.cwad.recyclerView.listeners.DiagonalBlockerTouchListener
import sa.cwad.screens.main.tabs.healthPlan.DatePresenter
import sa.cwad.screens.main.tabs.healthPlan.viewModels.MonthViewModel
import sa.cwad.screens.main.tabs.healthPlan.adapters.CalendarAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.DailyLoadedRecyclerViewAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.MonthLoadedRecyclerViewAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.OnItemListener
import sa.cwad.screens.main.tabs.healthPlan.models.EventForDate
import sa.cwad.utils.viewModelCreator
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MonthFragment : Fragment(R.layout.fragment_month), OnItemListener {

    private val viewModel by viewModelCreator { MonthViewModel() }

    @Inject lateinit var datePresenter: DatePresenter

    private val rowsArrayList = arrayListOf<List<LocalDate?>>()

    private var isLoading = false

    private lateinit var binding: FragmentMonthBinding
//    private var firstSelectTime: Long = 0
//    private val doubleClickTime = 500
//    private var formattedDate: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMonthBinding.bind(view)
        initData()
        initAdapter()
        initListeners()
        binding.weekly.setOnClickListener {
            findNavController().navigate(R.id.action_monthFragment_to_weekFragment)
        }
    }

    private fun initData() {
        var date = viewModel.date.minusDays(1)
        for (i in 0 until 10) {
            rowsArrayList.add(viewModel.daysInMonthList(date))
            date = date.plusDays(1)
            viewModel.date = date
        }
    }

    private fun initAdapter() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter =
            MonthLoadedRecyclerViewAdapter(datePresenter, rowsArrayList, ::goBackButton, ::goNextButton)
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
        viewModel.date = rowsArrayList.last().last()!!
        initData()
        notifyAdapter()
    }

    private fun loadDownMore() {
        val dateFirst = rowsArrayList[0].first()!!
        val lastDay = dateFirst.minusMonths(1)
        rowsArrayList.add(index = 0, element = viewModel.daysInMonthList(lastDay))

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

    override fun invoke(position: Int, date: LocalDate?) {
        viewModel.date = date!!
//        val date = date + " " + CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
//        if (date != null) {
//            viewModel.date = date
//            setMonthView()
//            when {
//                firstSelectTime == 0L -> {
//                    // Первый выбор даты
//                    firstSelectTime = System.currentTimeMillis()
//                    formattedDate = date
//                }
//
//                (System.currentTimeMillis() - firstSelectTime <= doubleClickTime) && (formattedDate == date) -> {
//                    // Время между двумя выборами меньше заданной задержки, считаем это двойным кликом
//                    val message = "Selected day " + dayText + " " + CalendarUtils.monthYearFromDate(
//                        CalendarUtils.selectedDate
//                    )
//                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                    // Сброс времени первого выбора, чтобы готовиться к следующему двойному клику
//                    firstSelectTime = 0
//                    formattedDate = date
//                }
//
//                else -> {
//                    // Сброс времени первого выбора, если это не был двойной клик
//                    firstSelectTime = 0
//                    formattedDate = date
//                }
//            }
//        }
    }
}
