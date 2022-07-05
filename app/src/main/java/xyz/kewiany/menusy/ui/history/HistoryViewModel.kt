package xyz.kewiany.menusy.ui.history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.ui.history.HistoryViewModel.Event
import xyz.kewiany.menusy.ui.history.HistoryViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
) : BaseViewModel<State, Event>(State()) {

    init {
        viewModelScope.launch { load() }
    }

    override fun handleEvent(event: Event) {

    }

    private suspend fun load() {
        val orderedProducts = orderRepository.getOrdersFromHistory()
        val items = orderedProducts.map { orderEntity ->
            with(orderEntity) {
                HistoryUiItem(
                    productName = productName,
                    productDescription = productDescription,
                    productPrice = productPrice,
                    quantity = quantity.toString()
                )
            }
        }
        updateState { it.copy(items = items) }
    }

    data class State(
        val items: List<HistoryUiItem> = emptyList()
    )

    object Event
}