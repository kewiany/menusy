package xyz.kewiany.menusy.ui.menu

import xyz.kewiany.menusy.ui.menu.MenuViewModel.Event
import xyz.kewiany.menusy.ui.menu.MenuViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class MenuViewModel : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}