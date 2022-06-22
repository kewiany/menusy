package xyz.kewiany.menusy.ui.history

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.ui.history.HistoryViewModel.Event
import xyz.kewiany.menusy.ui.history.HistoryViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
) : BaseViewModel<State, Event>(State) {

    init {
        viewModelScope.launch {
            while (true) {
                load()
                delay(5000L)
            }
        }
    }

    private suspend fun load() {
        val orderedProducts = orderRepository.getOrdersFromHistory()
        println(orderedProducts)
    }

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}