package xyz.kewiany.menusy.presentation.features.welcome

import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.Event
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.Event.FoodButtonClicked
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.Event.ShowProgress
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.State
import xyz.kewiany.menusy.presentation.navigation.NavigationDirections
import xyz.kewiany.menusy.presentation.navigation.Navigator
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        FoodButtonClicked -> navigator.navigate(NavigationDirections.menuEntry)
        is ShowProgress -> handleShowProgress(event)
    }

    private fun handleShowProgress(event: ShowProgress) {
        updateState {
            it.copy(showProgress = event.show)
        }
    }

    data class State(
        val showProgress: Boolean = false
    )

    sealed class Event {
        object FoodButtonClicked : Event()
        data class ShowProgress(val show: Boolean) : Event()
    }
}