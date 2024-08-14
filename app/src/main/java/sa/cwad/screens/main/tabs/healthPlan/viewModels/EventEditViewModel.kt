package sa.cwad.screens.main.tabs.healthPlan.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sa.cwad.model.AccountAlreadyExistsException
import sa.cwad.model.EmptyFieldException
import sa.cwad.model.PasswordMismatchException
import sa.cwad.model.accounts.AccountsRepository
import sa.cwad.model.accounts.entities.SignUpData
import sa.cwad.screens.main.tabs.healthPlan.EventService
import sa.cwad.screens.main.tabs.healthPlan.models.EventsRepository
import sa.cwad.screens.main.tabs.healthPlan.models.entities.Event
import sa.cwad.screens.main.tabs.healthPlan.models.entities.HourEvent
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EventEditViewModel  @Inject constructor(private val repository: EventsRepository)
 : ViewModel() {

    var date: LocalDate = LocalDate.now()

//    fun hourEventsListForDate(): List<HourEvent> {
//
//        val list = mutableListOf<HourEvent>()
//        for (i in 0..23) {
//            val time = LocalTime.of(i, 0)
//            val events = eventService.eventsForDateAndTime(date, time)
//            val hourEvent = HourEvent(time, events)
//            list.add(hourEvent)
//        }
//        return list
//    }

    fun createEvent(event: Event) {
        viewModelScope.launch {
//            try {
                repository.createEvent(event = event)
//                showSuccessSignUpMessage()
//                goBack()
//            } catch (e: EmptyFieldException) {
//                processEmptyFieldException(e)
//            } catch (e: PasswordMismatchException) {
//                processPasswordMismatchException()
//            } catch (e: AccountAlreadyExistsException) {
//                processAccountAlreadyExistsException()
//            } finally {
//                hideProgress()
//            }
        }
    }

    fun saveEvent(eventName: String, date: LocalDate, time: LocalTime) {
        val newEvent = Event(eventName, date, time)
        createEvent(event = newEvent)
//        eventService.eventsList.add(newEvent)
    }
}