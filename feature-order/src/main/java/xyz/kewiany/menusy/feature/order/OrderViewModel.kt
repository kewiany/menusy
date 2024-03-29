package xyz.kewiany.menusy.feature.order

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.android.common.BaseViewModel
import xyz.kewiany.menusy.common.Loggable
import xyz.kewiany.menusy.domain.usecase.order.DeleteOrderedProductUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.domain.usecase.order.PurchaseUseCase
import xyz.kewiany.menusy.feature.order.OrderViewModel.Event
import xyz.kewiany.menusy.feature.order.OrderViewModel.State
import xyz.kewiany.menusy.model.OrderedProduct
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase,
    private val deleteOrderedProductUseCase: DeleteOrderedProductUseCase,
    private val purchaseUseCase: PurchaseUseCase
) : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        Event.TriggerLoadOrder -> handleLoadOrderTriggered()
        Event.PayButtonClicked -> handlePayButtonClicked()
        is Event.DeleteProductClicked -> handleDeleteProductClicked(event)
    }

    private fun handleLoadOrderTriggered() {
        viewModelScope.launch {
            loadOrderedProducts()
        }
    }

    private fun handlePayButtonClicked() {
        viewModelScope.launch {
            purchaseUseCase()
            updateState { it.copy(results = emptyList()) }
        }
    }

    private fun handleDeleteProductClicked(event: Event.DeleteProductClicked) {
        viewModelScope.launch {
            deleteOrderedProductUseCase(event.productId)
            loadOrderedProducts()
        }
    }

    private suspend fun loadOrderedProducts() {
        val data = getOrderedProductsUseCase()
        updateState {
            it.copy(
                results = data.products,
                totalQuantity = data.totalQuantity.toString(),
                totalPrice = data.totalPrice.toString()
            )
        }
    }

    data class State(
        val results: List<OrderedProduct> = emptyList(),
        val totalQuantity: String? = null,
        val totalPrice: String? = null
    )

    sealed class Event {
        object TriggerLoadOrder : Event()
        object PayButtonClicked : Event()
        data class DeleteProductClicked(val productId: String) : Event(), Loggable
    }
}