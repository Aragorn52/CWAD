package sa.cwad.screens.main.tabs.healthPlan

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import sa.cwad.R
import sa.cwad.databinding.FragmentDailyBinding
import sa.cwad.screens.main.tabs.healthPlan.adapters.DailyAdapter
import sa.cwad.screens.main.tabs.healthPlan.adapters.RecyclerViewAdapter
import sa.cwad.screens.main.tabs.healthPlan.models.EventForDate
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class DailyFragment : Fragment(R.layout.fragment_daily) {

    @Inject
    lateinit var datePresenter: DatePresenter

    private val viewModel by viewModels<DailyViewModel>()

    private lateinit var binding: FragmentDailyBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
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
//        showDay(viewModel.date)

        recyclerView = binding.recyclerView
        populateData()
        initAdapter()
        initScrollListener()
        binding.recyclerView.addOnItemTouchListener(DiagonalBlockerTouchListener(true, 300F))

        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_eventEditFragment)
        }
    }

//    private fun showDay(date: LocalDate) {
//        binding.monthDayTV.text = datePresenter.monthDayFromDate(date)
//        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
//        binding.dayOfWeekTV.text = dayOfWeek
//        viewModel.date = date
//    }

    private fun populateData() {
        var date = viewModel.date
        for (i in 0 until 10) {

            val element = EventForDate(
                date,
                viewModel.hourEventsListForDate(date)
            )
            rowsArrayList.add(element)
            date = date.plusDays(1)
        }
    }

    private fun initAdapter() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAdapter = RecyclerViewAdapter(datePresenter, rowsArrayList)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = manager

        val snapHelper: SnapHelper = PagerSnapHelper()
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(binding.recyclerView)
        manager.scrollToPositionWithOffset(1, 0)
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisibleItemPosition = layoutManager?.findLastCompletelyVisibleItemPosition()
                val firstVisibleItemPosition =
                    layoutManager?.findFirstCompletelyVisibleItemPosition()

                if (!isLoading && (lastVisibleItemPosition == rowsArrayList.size - 1 || firstVisibleItemPosition == 0)) {
                    loadMore()
                    isLoading = true
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        // Добавляем элемент в начало списка для прокрутки влево
        rowsArrayList.add(
            0, EventForDate(
                viewModel.date.minusDays(1),
                viewModel.hourEventsListForDate(viewModel.date.minusDays(1))
            )
        )

        // Уведомляем адаптер о добавлении элемента
        recyclerViewAdapter.notifyItemInserted(0)

        // Задержка имитирует загрузку данных
        Handler(Looper.getMainLooper()).postDelayed({
            // Удаляем временный элемент из начала списка
            rowsArrayList.removeAt(0)
            recyclerViewAdapter.notifyItemRemoved(0)

            // Добавляем элементы в конец списка для прокрутки вправо
            for (i in 0 until 10) {
                viewModel.date = viewModel.date.plusDays(1)
                rowsArrayList.add(
                    EventForDate(
                        viewModel.date,
                        viewModel.hourEventsListForDate(viewModel.date)
                    )
                )
            }

            recyclerViewAdapter.notifyDataSetChanged()
            isLoading = false
        }, 0L) // Задержка в 500 мс
    }
}