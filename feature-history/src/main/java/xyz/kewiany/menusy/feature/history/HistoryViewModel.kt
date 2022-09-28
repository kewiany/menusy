package xyz.kewiany.menusy.feature.history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.android.common.BaseViewModel
import xyz.kewiany.menusy.domain.usecase.order.GetOrdersFromHistoryUseCase
import xyz.kewiany.menusy.feature.history.HistoryViewModel.Event
import xyz.kewiany.menusy.feature.history.HistoryViewModel.State
import xyz.kewiany.menusy.model.HistoryProduct
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getOrdersFromHistoryUseCase: GetOrdersFromHistoryUseCase,
    private val historyOrderMapper: HistoryOrderMapper
) : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        Event.TriggerLoadHistory -> handleLoadHistoryTriggered()
    }

    private fun handleLoadHistoryTriggered() {
        viewModelScope.launch { load() }
    }

    private suspend fun load() {
        val items = mutableListOf<HistoryUiItem>()
        val orders = getOrdersFromHistoryUseCase()
        orders.map { order ->
            val historyOrder = historyOrderMapper.asUIItem(order)
            items.add(historyOrder)
            val historyProducts = order.products.map(HistoryProduct::asUIItem)
            items.addAll(historyProducts)
        }
        updateState { it.copy(items = items) }
    }

    data class State(
        val items: List<HistoryUiItem> = emptyList()
    )

    sealed class Event {
        object TriggerLoadHistory : Event()
    }
}