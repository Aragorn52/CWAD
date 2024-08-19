package sa.cwad.screens.main.tabs.healthPlan.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import sa.cwad.model.settings.AppSettings
import sa.cwad.screens.main.tabs.healthPlan.EventService
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import sa.cwad.screens.main.tabs.healthPlan.models.entities.HourEvent
import sa.cwad.utils.MutableLiveEvent
import sa.cwad.utils.publishEvent
import sa.cwad.utils.share
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val eventService: EventService,
    private val repository: EventsRepository,
    private val appSettings: AppSettings
) : ViewModel() {


    private val _getEventListEvent = MutableLiveData<List<Event?>>()
    val getEventListEvent = _getEventListEvent.share()

    var date: LocalDate = LocalDate.now()
    init {
        viewModelScope.launch {
            repository.getEventsByAccountIdAndDate(accountId = appSettings.getCurrentAccountId(), date)
                .collect { eventList ->
                    _getEventListEvent.postValue(eventList)
                }
        }
    }

    fun searchEventListOnDate(selectDate: LocalDate) {
        viewModelScope.launch {
            repository.getEventsByAccountIdAndDate(accountId = appSettings.getCurrentAccountId(), date)
                .collect { eventList ->
                    eventList.filter { it?.date == selectDate }
                    _getEventListEvent.postValue(eventList)
                }
        }
    }

    suspend fun getEventList(selectDate: LocalDate): List<Event?> {

        val res =  viewModelScope.async {
            val res = mutableListOf<Event?>();
             repository.getEventsByAccountIdAndDate(accountId = appSettings.getCurrentAccountId(), date)
                .collect { eventList ->
                    res.clear()
                    res.addAll(eventList.filter { it?.date == selectDate })
                }
            res
        }
        val t = res
        return mutableListOf()
    }
}