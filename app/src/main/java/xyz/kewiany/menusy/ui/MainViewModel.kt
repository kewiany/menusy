package xyz.kewiany.menusy.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.MainViewModel.Event
import xyz.kewiany.menusy.ui.MainViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator
) : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) = when (event) {
        Event.MenuClicked -> navigator.navigate(NavigationDirections.menuEntry)
        Event.OrderClicked -> navigator.navigate(NavigationDirections.order)
        Event.SearchClicked -> navigator.navigate(NavigationDirections.search)
    }

    object State
    sealed class Event {
        object MenuClicked : Event()
        object OrderClicked : Event()
        object SearchClicked : Event()
    }
}