package xyz.kewiany.menusy.ui.menu.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.entity.Product
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.ui.search.ProductUItemModifier
import xyz.kewiany.menusy.usecase.GetMenuResponse
import xyz.kewiany.menusy.usecase.GetMenuUseCase
import xyz.kewiany.menusy.utils.*

class MenuItemsViewModel @AssistedInject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val orderRepository: OrderRepository,
    @Assisted private val menuId: String,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    private val cachedProducts = mutableListOf<Product>()

    override fun handleEvent(event: Event) = when (event) {
        is Event.LoadMenu -> handleLoadMenu()
        is Event.TabClicked -> handleTabClicked(event)
        is Event.DecreaseQuantityClicked -> handleDecreaseQuantity(event)
        is Event.IncreaseQuantityClicked -> handleIncreaseQuantity(event)
    }

    private fun handleLoadMenu() {
        viewModelScope.launch {
            loadMenu(menuId)
        }
    }

    private fun handleTabClicked(event: Event.TabClicked) {
        val id = event.id
        val tabs = state.value.tabs
        val indexOnTabs = tabs.indexOfFirst { item -> item.id == id }
        val items = state.value.items
        val indexOnList = items.indexOfFirst { item -> if (item is CategoryUiItem) item.id == id else false }
        updateState { it.copy(currentTab = indexOnTabs, currentCategory = indexOnList) }
    }

    private fun handleDecreaseQuantity(event: Event.DecreaseQuantityClicked) {
        val productId = event.productId
        val items = ProductUItemModifier.decreaseQuantity(state.value.items, productId)
        updateState { it.copy(items = items) }
        updateOrder(items, productId)
    }

    private fun handleIncreaseQuantity(event: Event.IncreaseQuantityClicked) {
        val productId = event.productId
        val items = ProductUItemModifier.increaseQuantity(state.value.items, productId)
        updateState { it.copy(items = items) }
        updateOrder(items, productId)
    }

    private fun updateOrder(uiItems: List<UiItem>, productId: String) {
        val productQuantity = (uiItems.first { it.id == productId } as ProductUiItem).quantity
        val product = cachedProducts.first { it.id == productId }
        viewModelScope.launch {
            orderRepository.updateOrder(productQuantity, product)
        }
    }

    private suspend fun loadMenu(menuId: String) {
        try {
            updateState { it.copy(showLoading = true) }
            when (val response = getMenuUseCase(menuId)) {
                is GetMenuResponse.Success -> {
                    val categories = response.menu.categories
                    val products = response.products

                    cachedProducts.clear()
                    cachedProducts.addAll(products)

                    val tabs = categories?.map { category -> CategoryTab(category.id, category.name) } ?: emptyList()
                    val orderedProducts = orderRepository.getOrderedProducts()
                    val items = obtainUiItems(categories, products, orderedProducts)

                    updateState { it.copy(tabs = tabs, items = items, showLoading = false) }
                }
                is GetMenuResponse.Error -> {
                    updateState { it.copy(showError = SingleEvent(Unit), showLoading = false) }
                }
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(
        val tabs: List<CategoryTab> = emptyList(),
        val currentTab: Int = 0,
        val items: List<UiItem> = emptyList(),
        val currentCategory: Int = 0,
        val showLoading: Boolean = false,
        val showError: SingleEvent<Unit>? = null
    )

    sealed class Event {
        object LoadMenu : Event()
        data class TabClicked(val id: String) : Event()
        data class IncreaseQuantityClicked(val productId: String) : Event()
        data class DecreaseQuantityClicked(val productId: String) : Event()
    }

    @AssistedFactory
    interface Factory {
        fun create(menuId: String): MenuItemsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory, menuId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(menuId) as T
            }
        }
    }
}

data class CategoryTab(val id: String, val name: String)