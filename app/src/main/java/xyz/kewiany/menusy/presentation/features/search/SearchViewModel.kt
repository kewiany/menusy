package xyz.kewiany.menusy.presentation.features.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.usecase.menu.GetProductsByQueryResponse
import xyz.kewiany.menusy.domain.usecase.menu.GetProductsByQueryUseCase
import xyz.kewiany.menusy.domain.usecase.order.UpdateOrderUseCase
import xyz.kewiany.menusy.domain.usecase.search.ClearSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.GetSearchTextUseCase
import xyz.kewiany.menusy.presentation.features.menu.items.ProductUiItem
import xyz.kewiany.menusy.presentation.features.search.SearchViewModel.Event
import xyz.kewiany.menusy.presentation.features.search.SearchViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import xyz.kewiany.menusy.presentation.utils.SingleEvent
import xyz.kewiany.menusy.presentation.utils.UiItem
import xyz.kewiany.menusy.presentation.utils.obtainUiItems
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val clearSearchTextUseCase: ClearSearchTextUseCase,
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase,
    private val getSearchTextUseCase: GetSearchTextUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) : BaseViewModel<State, Event>(State()) {

    private val cachedProducts = mutableListOf<Product>()

    init {
        searchTextFlow().launchIn(viewModelScope)
    }

    private fun searchTextFlow() = getSearchTextUseCase()
        .debounce(500L)
        .onEach { text -> searchProducts(text) }

    override fun handleEvent(event: Event) = when (event) {
        is Event.DecreaseQuantityClicked -> handleDecreaseQuantity(event)
        is Event.IncreaseQuantityClicked -> handleIncreaseQuantity(event)
    }

    private fun handleDecreaseQuantity(event: Event.DecreaseQuantityClicked) {
        val productId = event.productId
        val items = ProductUItemModifier.decreaseQuantity(state.value.results, productId)
        updateState { it.copy(results = items) }
        updateOrder(items, productId)
    }

    private fun handleIncreaseQuantity(event: Event.IncreaseQuantityClicked) {
        val productId = event.productId
        val items = ProductUItemModifier.increaseQuantity(state.value.results, productId)
        updateState { it.copy(results = items) }
        updateOrder(items, productId)
    }

    private fun updateOrder(uiItems: List<UiItem>, productId: String) {
        val productQuantity = (uiItems.first { it.id == productId } as ProductUiItem).quantity
        val product = cachedProducts.first { it.id == productId }
        viewModelScope.launch { updateOrderUseCase(productQuantity, product) }
    }

    private suspend fun searchProducts(query: String) {
        if (query.isEmpty()) return

        try {
            updateState { it.copy(showLoading = true) }
            when (val response = getProductsByQueryUseCase(query)) {
                is GetProductsByQueryResponse.Success -> {
                    val items = obtainUiItems(response.products, response.orderedProducts)

                    cachedProducts.clear()
                    cachedProducts.addAll(response.products)

                    updateState { it.copy(showLoading = false, results = items) }
                }
                is GetProductsByQueryResponse.Error -> {
                    updateState { it.copy(showError = SingleEvent(Unit), showLoading = false) }
                }
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    override fun onCleared() {
        clearSearchTextUseCase()
        super.onCleared()
    }

    data class State(
        val showError: SingleEvent<Unit>? = null,
        val showLoading: Boolean = false,
        val searchText: String = "",
        val results: List<UiItem> = emptyList()
    )

    sealed class Event {
        data class IncreaseQuantityClicked(val productId: String) : Event()
        data class DecreaseQuantityClicked(val productId: String) : Event()
    }
}