package xyz.kewiany.menusy.feature.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.android.common.BaseViewModel
import xyz.kewiany.menusy.android.common.ContentBuilder
import xyz.kewiany.menusy.android.common.ProductQuantityModifier
import xyz.kewiany.menusy.android.common.ProductSelectionModifier
import xyz.kewiany.menusy.common.Loggable
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.domain.usecase.menu.GetProductsByQueryUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.domain.usecase.order.UpdateOrderUseCase
import xyz.kewiany.menusy.domain.usecase.search.ClearSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.GetSearchTextUseCase
import xyz.kewiany.menusy.feature.search.SearchViewModel.Event
import xyz.kewiany.menusy.feature.search.SearchViewModel.State
import xyz.kewiany.menusy.model.Product
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val clearSearchTextUseCase: ClearSearchTextUseCase,
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase,
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase,
    private val getSearchTextUseCase: GetSearchTextUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val contentBuilder: ContentBuilder,
    private val productSelectionModifier: ProductSelectionModifier,
    private val productQuantityModifier: ProductQuantityModifier,
) : BaseViewModel<State, Event>(State()) {

    init {
        searchTextFlow().launchIn(viewModelScope)
    }

    private fun searchTextFlow() = getSearchTextUseCase()
        .debounce(500L)
        .onEach { text -> eventHandler(Event.SearchTextChanged(text)) }

    override fun handleEvent(event: Event) = when (event) {
        is Event.TriggerDismissError -> handleDismissErrorTriggered()
        is Event.ErrorOKClicked -> handleErrorOKClicked()
        is Event.SearchTextChanged -> handleSearchTextChanged(event)
        is Event.ProductClicked -> handleProductClicked(event)
        is Event.DecreaseQuantityClicked -> handleDecreaseQuantity(event)
        is Event.IncreaseQuantityClicked -> handleIncreaseQuantity(event)
    }

    private fun handleDismissErrorTriggered() {
        updateState { it.copy(showError = false) }
    }

    private fun handleErrorOKClicked() {
        updateState { it.copy(showError = false) }
    }

    private fun handleSearchTextChanged(event: Event.SearchTextChanged) {
        val text = event.text
        if (text.isEmpty()) {
            updateState { it.copy(results = emptyList()) }
            return
        }
        viewModelScope.launch { searchProducts(text) }
    }

    private fun handleProductClicked(event: Event.ProductClicked) {
        val items = state.value.results
        val productId = event.id

        val (newQuantity, newItems) = productSelectionModifier.select(items, productId)
        updateState { it.copy(results = newItems) }

        viewModelScope.launch { updateOrder(newQuantity, productId) }
    }

    private fun handleDecreaseQuantity(event: Event.DecreaseQuantityClicked) {
        val productId = event.productId
        val items = state.value.results

        val (newQuantity, newItems) = productQuantityModifier.decreaseQuantity(items, productId) ?: return
        updateState { it.copy(results = newItems) }

        viewModelScope.launch { updateOrder(newQuantity, productId) }
    }

    private fun handleIncreaseQuantity(event: Event.IncreaseQuantityClicked) {
        val productId = event.productId
        val items = state.value.results

        val (newQuantity, newItems) = productQuantityModifier.increaseQuantity(items, productId) ?: return
        updateState { it.copy(results = newItems) }

        viewModelScope.launch { updateOrder(newQuantity, productId) }
    }

    private suspend fun updateOrder(quantity: Int, productId: String) {
        try {
            updateOrderUseCase(quantity, productId)
        } catch (e: CancellationException) {
            println(e)
        }
    }

    private suspend fun searchProducts(query: String) {
        updateState { it.copy(showLoading = true) }
        when (val result = getProductsByQueryUseCase(query)) {
            is Result.Success -> handleProducts(result.data)
            is Result.Error -> handleError()
        }
    }

    private suspend fun handleProducts(products: List<Product>) {
        val orderedProducts = getOrderedProductsUseCase().products

        val items = contentBuilder.buildContent(
            categories = emptyList(),
            products = products,
            orderedProducts = orderedProducts
        )
        updateState { it.copy(showLoading = false, results = items) }
    }

    private fun handleError() {
        updateState { it.copy(showError = true, showLoading = false) }
    }

    override fun onCleared() {
        clearSearchTextUseCase()
        super.onCleared()
    }

    data class State(
        val showError: Boolean = false,
        val showLoading: Boolean = false,
        val searchText: String = "",
        val results: List<UiItem> = emptyList()
    )

    sealed class Event {
        object TriggerDismissError : Event()
        object ErrorOKClicked : Event()
        data class SearchTextChanged(val text: String) : Event(), Loggable
        data class ProductClicked(val id: String) : Event(), Loggable
        data class IncreaseQuantityClicked(val productId: String) : Event(), Loggable
        data class DecreaseQuantityClicked(val productId: String) : Event(), Loggable
    }
}