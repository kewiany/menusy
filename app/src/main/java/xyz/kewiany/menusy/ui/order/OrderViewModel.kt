package xyz.kewiany.menusy.ui.order

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.OrderedProduct
import xyz.kewiany.menusy.ui.order.OrderViewModel.Event
import xyz.kewiany.menusy.ui.order.OrderViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
) : BaseViewModel<State, Event>(State()) {


    override fun handleEvent(event: Event) = when (event) {
        Event.PayButtonClicked -> handlePayButtonClicked()
    }

    private fun handlePayButtonClicked() {
        val order = state.value.results
        viewModelScope.launch { orderRepository.saveOrderToHistory(order) }
    }

    data class State(
        val results: List<OrderedProduct> = emptyList()
    )

    sealed class Event {
        object PayButtonClicked : Event()
    }
}