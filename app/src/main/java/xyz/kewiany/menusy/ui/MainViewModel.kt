package xyz.kewiany.menusy.ui

import xyz.kewiany.menusy.ui.utils.Navigator
import xyz.kewiany.menusy.ui.utils.Screen
import xyz.kewiany.menusy.utils.BaseViewModel

class MainViewModel(private val navigator: Navigator) : BaseViewModel<MainViewModel.State, MainViewModel.Event>(State) {

    override fun handleEvent(event: Event) = when (event) {
        Event.MenuClicked -> navigator.navigate(Screen.MenuEntryScreen)
        Event.OrderClicked -> navigator.navigate(Screen.OrderScreen)
    }

    object State
    sealed class Event {
        object MenuClicked : Event()
        object OrderClicked : Event()
    }
}