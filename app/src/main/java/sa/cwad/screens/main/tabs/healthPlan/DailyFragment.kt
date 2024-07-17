package sa.cwad.screens.main.tabs.healthPlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import sa.cwad.R
import sa.cwad.databinding.FragmentDailyBinding
import sa.cwad.screens.main.tabs.healthPlan.adapters.DailyLoadedAdapter
import sa.cwad.screens.main.tabs.healthPlan.models.EventForDate
import javax.inject.Inject

@AndroidEntryPoint
class DailyFragment : Fragment(R.layout.fragment_daily) {

    @Inject
    lateinit var datePresenter: DatePresenter
    @Inject
    lateinit var creator: BaseConfigurationCalendarCreator<RecyclerView.ViewHolder>

    private val viewModel by viewModels<DailyViewModel>()

    private lateinit var binding: FragmentDailyBinding

    private val rowsArrayList = arrayListOf<EventForDate?>()

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
        initRecyclerView()
        initEventData()

        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_eventEditFragment)
        }
    }

    private fun initEventData() {
        var date = viewModel.date.minusDays(100)
        for (i in 0 until 201) {
            rowsArrayList.add(EventForDate(date, viewModel.hourEventsListForDate(date)))
            date = date.plusDays(1)
            viewModel.date = date
        }
    }

    private fun initRecyclerView() {

        val rvAdapter = DailyLoadedAdapter(datePresenter, rowsArrayList, ::goBackButton, ::goNextButton)
        val rv = binding.recyclerView
        val lastElement = rowsArrayList.size - 1

        creator.initAdapter(requireContext(), recyclerView = rv, recyclerViewAdapter = rvAdapter)
        creator.initListeners(rv, rvAdapter, ::loadUpMore, ::loadDownMore, lastElement, 0)
    }

    private fun loadUpMore() {
        for (i in 0 until 100) {
            val dateLast = rowsArrayList.last()!!.date
            val actualDate = dateLast.plusDays(1)
            rowsArrayList.add(
                EventForDate(
                    dateLast.plusDays(1),
                    viewModel.hourEventsListForDate(actualDate)
                )
            )
        }
    }

    private fun goBackButton() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager?
        val lastVisibleItemPosition = layoutManager!!.findLastCompletelyVisibleItemPosition()
        if (lastVisibleItemPosition == 0) {
            binding.recyclerView.adapter?.let { creator.loadingData(it, ::loadDownMore) }
        }
        binding.recyclerView.scrollToPosition(lastVisibleItemPosition - 1)
    }

    private fun goNextButton() {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager?
        val lastVisibleItemPosition = layoutManager!!.findLastCompletelyVisibleItemPosition()
        if (lastVisibleItemPosition == rowsArrayList.size - 1) {
            binding.recyclerView.adapter?.let { creator.loadingData(it, ::loadUpMore) }
        }
        binding.recyclerView.scrollToPosition(lastVisibleItemPosition + 1)
    }

    private fun loadDownMore() {
        val dateFirst = rowsArrayList[0]!!.date
        // Добавляем элемент в начало списка для прокрутки влево
        rowsArrayList.add(
            index = 0,
            element = EventForDate(
                dateFirst.minusDays(1),
                viewModel.hourEventsListForDate(dateFirst.minusDays(1))
            )
        )

        // Уведомляем адаптер о добавлении элемента
        binding.recyclerView.adapter?.notifyItemInserted(0)
    }
}