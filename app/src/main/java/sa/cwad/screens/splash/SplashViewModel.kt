package sa.cwad.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sa.model.accounts.AccountsRepository
import sa.cwad.utils.MutableLiveEvent
import sa.cwad.utils.publishEvent
import sa.cwad.utils.share

/**
 * SplashViewModel checks whether user is signed-in or not.
 */
class SplashViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(accountsRepository.isSignedIn())
        }
    }
}