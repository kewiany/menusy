package xyz.kewiany.menusy.ui.order

import xyz.kewiany.menusy.ui.order.OrderViewModel.Event
import xyz.kewiany.menusy.ui.order.OrderViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class OrderViewModel : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}