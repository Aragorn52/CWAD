package sa.cwad.screens.main.tabs.healthPlan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import sa.cwad.R
import sa.cwad.databinding.FragmentDailyBinding
import sa.cwad.screens.main.tabs.healthPlan.adapters.DailyAdapter
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
        setHourAdapter()
        showDay(viewModel.date)

        binding.newEventBT.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_eventEditFragment)
        }
    }

    private fun showDay(date: LocalDate) {
        binding.monthDayTV.text = datePresenter.monthDayFromDate(date)
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek
        viewModel.date = date
    }

    private fun setHourAdapter() {

        binding.recyclerView.apply {
            adapter = DailyAdapter(datePresenter, viewModel.hourEventsListForDate())
            val manager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            layoutManager = manager

            val snapHelper: SnapHelper = PagerSnapHelper()
            manager.scrollToPositionWithOffset(15, 0)
            onFlingListener = null
            snapHelper.attachToRecyclerView(binding.recyclerView)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var lastVisibleItemPosition = -1

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val currentFirstVisibleItemPosition =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (currentFirstVisibleItemPosition == lastVisibleItemPosition + 1) {
                        showDay(viewModel.date.plusDays(1))
                        (adapter as DailyAdapter).changeList(viewModel.hourEventsListForDate())
                    }

                    if (currentFirstVisibleItemPosition == lastVisibleItemPosition - 1) {
                        showDay(viewModel.date.minusDays(1))
                        (adapter as DailyAdapter).changeList(viewModel.hourEventsListForDate())
                    }
                    lastVisibleItemPosition = currentFirstVisibleItemPosition
                }
            })
        }
    }
}