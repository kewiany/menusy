package xyz.kewiany.menusy.ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.data.repository.MenuRepository
import xyz.kewiany.menusy.data.repository.OrderRepository
import xyz.kewiany.menusy.data.repository.SearchRepository
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.ui.menu.items.ProductUiItem
import xyz.kewiany.menusy.ui.search.SearchViewModel.Event
import xyz.kewiany.menusy.ui.search.SearchViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.UiItem
import xyz.kewiany.menusy.utils.obtainUiItems
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel<State, Event>(State()) {

    private val cachedProducts = mutableListOf<Product>()

    init {
        searchTextFlow().launchIn(viewModelScope)
    }

    private fun searchTextFlow() = searchRepository.searchText
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
        viewModelScope.launch { orderRepository.updateOrder(productQuantity, product) }
    }

    private suspend fun searchProducts(query: String) {
        if (query.isEmpty()) return
        val products = menuRepository.getProductsByQuery(query)
        val orderedProducts = orderRepository.getOrderedProducts()
        val items = obtainUiItems(products, orderedProducts)

        cachedProducts.clear()
        cachedProducts.addAll(products)

        updateState { it.copy(results = items) }
    }

    override fun onCleared() {
        searchRepository.clearSearchText()
        super.onCleared()
    }

    data class State(
        val searchText: String = "",
        val results: List<UiItem> = emptyList()
    )

    sealed class Event {
        data class IncreaseQuantityClicked(val productId: String) : Event()
        data class DecreaseQuantityClicked(val productId: String) : Event()
    }
}