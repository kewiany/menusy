package xyz.kewiany.menusy.ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.MenuRepository
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.SearchRepository
import xyz.kewiany.menusy.entity.Product
import xyz.kewiany.menusy.ui.menu.items.ProductUiItem
import xyz.kewiany.menusy.ui.menu.items.obtainUiItems
import xyz.kewiany.menusy.ui.search.SearchViewModel.Event
import xyz.kewiany.menusy.ui.search.SearchViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.UiItem
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

    override fun handleEvent(event: Event) = when (event) {
        is Event.SearchItemClicked -> handleSearchItemClicked(event)
        is Event.DecreaseQuantityClicked -> handleDecreaseQuantity(event)
        is Event.IncreaseQuantityClicked -> handleIncreaseQuantity(event)
    }

    private fun handleSearchItemClicked(event: Event.SearchItemClicked) {
        println(event.id)
    }

    private fun handleDecreaseQuantity(event: Event.DecreaseQuantityClicked) {
        val results = state.value.results
        val index = results.indexOfFirst { it.id == event.productId }
        val product = results[index] as ProductUiItem
        val quantity = product.quantity - 1

        changeQuantity(index, product, quantity)
        updateOrder(quantity, product.id)
    }

    private fun handleIncreaseQuantity(event: Event.IncreaseQuantityClicked) {
        val results = state.value.results
        val index = results.indexOfFirst { it.id == event.productId }
        val product = results[index] as ProductUiItem
        val quantity = product.quantity + 1

        changeQuantity(index, product, quantity)
        updateOrder(quantity, product.id)
    }

    private fun changeQuantity(index: Int, product: ProductUiItem, quantity: Int) {
        val newProduct = ProductUiItem(product.id, product.name, product.description, product.price, quantity)
        updateState {
            val items = it.results.toMutableList()
            items.apply {
                remove(product)
                add(index, newProduct)
            }
            it.copy(results = items)
        }
    }

    private fun updateOrder(quantity: Int, productId: String) = viewModelScope.launch {
        val product = cachedProducts.first { it.id == productId }
        orderRepository.updateOrder(quantity, product)
    }

    private fun searchTextFlow() = searchRepository.searchText
        .debounce(500L)
        .onEach { text ->
            if (text.isNotEmpty()) queryProducts(text)
        }

    private suspend fun queryProducts(query: String) {
        val products = menuRepository.getProductsByQuery(query)
        val orderedProducts = orderRepository.order.value
        val items = obtainUiItems(products, orderedProducts)

        cachedProducts.clear()
        cachedProducts.addAll(products)

        updateState {
            it.copy(results = items)
        }
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
        data class SearchItemClicked(val id: String) : Event()
        data class IncreaseQuantityClicked(val productId: String) : Event()
        data class DecreaseQuantityClicked(val productId: String) : Event()
    }
}