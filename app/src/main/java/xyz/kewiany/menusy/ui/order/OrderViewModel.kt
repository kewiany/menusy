package xyz.kewiany.menusy.ui.order

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.ui.order.OrderViewModel.Event
import xyz.kewiany.menusy.ui.order.OrderViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    orderRepository: OrderRepository
) : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    init {
        orderRepository.order
            .onEach {
                println(it)
            }
            .launchIn(viewModelScope)
    }

    object State
    object Event
}