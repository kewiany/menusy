package xyz.kewiany.menusy.presentation.features.order

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.usecase.order.FinishOrderUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel.Event
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase,
    private val finishOrderUseCase: FinishOrderUseCase
) : BaseViewModel<State, Event>(State()) {

    init {
        load()
    }

    override fun handleEvent(event: Event) = when (event) {
        Event.PayButtonClicked -> handlePayButtonClicked()
    }

    private fun load() {
        val orderedProducts = getOrderedProductsUseCase()
        updateState { it.copy(results = orderedProducts) }
    }

    private fun handlePayButtonClicked() {
        viewModelScope.launch {
            finishOrderUseCase()
            updateState { it.copy(results = emptyList()) }
        }
    }

    data class State(
        val results: List<OrderedProduct> = emptyList()
    )

    sealed class Event {
        object PayButtonClicked : Event()
    }
}