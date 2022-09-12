package xyz.kewiany.menusy.presentation.features.order

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.usecase.order.DeleteOrderedProductUseCase
import xyz.kewiany.menusy.domain.usecase.order.FinishOrderUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel.Event
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase,
    private val deleteOrderedProductUseCase: DeleteOrderedProductUseCase,
    private val finishOrderUseCase: FinishOrderUseCase
) : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        Event.TriggerLoadOrder -> handleLoadOrderTriggered()
        Event.PayButtonClicked -> handlePayButtonClicked()
        is Event.DeleteProductClicked -> handleDeleteProductClicked(event)
    }

    private fun handleLoadOrderTriggered() {
        val orderedProducts = getOrderedProductsUseCase()
        updateState { it.copy(results = orderedProducts) }
    }

    private fun handlePayButtonClicked() {
        viewModelScope.launch {
            finishOrderUseCase()
            updateState { it.copy(results = emptyList()) }
        }
    }

    private fun handleDeleteProductClicked(event: Event.DeleteProductClicked) {
        viewModelScope.launch {
            deleteOrderedProductUseCase(event.productId)
            val orderedProducts = getOrderedProductsUseCase()
            updateState { it.copy(results = orderedProducts) }
        }
    }

    data class State(
        val results: List<OrderedProduct> = emptyList()
    )

    sealed class Event {
        object TriggerLoadOrder : Event()
        object PayButtonClicked : Event()
        data class DeleteProductClicked(val productId: String) : Event()
    }
}