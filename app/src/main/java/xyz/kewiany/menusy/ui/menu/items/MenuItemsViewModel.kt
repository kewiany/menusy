package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class MenuItemsViewModel : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}