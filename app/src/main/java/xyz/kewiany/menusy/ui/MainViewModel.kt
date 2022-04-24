package xyz.kewiany.menusy.ui

import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.utils.BaseViewModel

class MainViewModel(private val navigator: Navigator) : BaseViewModel<MainViewModel.State, MainViewModel.Event>(State) {

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