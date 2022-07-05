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
        val items = mutableListOf<HistoryUiItem>()
        val orders = orderRepository.getOrdersFromHistory()
        orders.map { orderEntity ->
            with(orderEntity) {
                HistoryOrderUiItem(
                    id = id.toString(),
                    date = date,
                    totalPrice = totalPrice.toString(),
                    totalQuantity = totalQuantity.toString()
                ).also(items::add)
            }

            orderEntity.products.forEach { productEntity ->
                with(productEntity) {
                    HistoryProductUiItem(
                        id = id.toString(),
                        name = name,
                        description = description,
                        price = price,
                        quantity = quantity.toString()
                    ).also(items::add)
                }
            }
        }
        updateState { it.copy(items = items) }
    }

    data class State(
        val items: List<HistoryUiItem> = emptyList()
    )

    object Event
}