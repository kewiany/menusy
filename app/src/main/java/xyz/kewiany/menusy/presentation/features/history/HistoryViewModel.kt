package xyz.kewiany.menusy.presentation.features.history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.domain.model.HistoryProduct
import xyz.kewiany.menusy.domain.usecase.order.GetOrdersFromHistoryUseCase
import xyz.kewiany.menusy.presentation.features.history.HistoryViewModel.Event
import xyz.kewiany.menusy.presentation.features.history.HistoryViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import xyz.kewiany.menusy.presentation.utils.HistoryUiItem
import xyz.kewiany.menusy.presentation.utils.asUIItem
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getOrdersFromHistoryUseCase: GetOrdersFromHistoryUseCase
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
            val historyOrder = order.asUIItem()
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