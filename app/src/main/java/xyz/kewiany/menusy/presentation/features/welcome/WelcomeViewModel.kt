package xyz.kewiany.menusy.presentation.features.welcome

import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.kewiany.menusy.android.common.BaseViewModel
import xyz.kewiany.menusy.common.navigation.NavigationDirections
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.Event
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.Event.MenuButtonClicked
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.State
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) = when (event) {
        MenuButtonClicked -> navigator.navigate(NavigationDirections.menuEntry)
    }

    object State

    sealed class Event {
        object MenuButtonClicked : Event()
    }
}