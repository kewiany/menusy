package xyz.kewiany.menusy.ui.welcome

import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.Event
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.Event.FoodButtonClicked
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.Event.ShowProgress
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class WelcomeViewModel(private val navigator: Navigator) : BaseViewModel<State, Event>(State()) {

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