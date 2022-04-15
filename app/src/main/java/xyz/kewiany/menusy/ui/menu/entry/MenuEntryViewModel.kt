package xyz.kewiany.menusy.ui.menu.entry

import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class MenuEntryViewModel : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}