package xyz.kewiany.menusy.ui.menu.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.MenuRepository
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.OrderedProduct
import xyz.kewiany.menusy.entity.Category
import xyz.kewiany.menusy.entity.Product
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.ui.search.ProductUItemModifier
import xyz.kewiany.menusy.usecase.GetMenuResponse
import xyz.kewiany.menusy.usecase.GetMenuUseCase
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.DispatcherProvider
import xyz.kewiany.menusy.utils.SingleEvent
import xyz.kewiany.menusy.utils.UiItem

class MenuItemsViewModel @AssistedInject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val orderRepository: OrderRepository,
    menuRepository: MenuRepository,
    @Assisted private val menuId: String,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    private val cachedProducts = mutableListOf<Product>()

    init {
        viewModelScope.launch(dispatcherProvider.main()) {
            while (true) {
                loadMenu(menuId)
                menuRepository.needReloadProducts.first()
            }
        }
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.TabClicked -> handleTabClicked(event)
        is Event.ProductClicked -> println(event.id)
        is Event.DecreaseQuantityClicked -> handleDecreaseQuantity(event)
        is Event.IncreaseQuantityClicked -> handleIncreaseQuantity(event)
    }

    private fun handleTabClicked(event: Event.TabClicked) {
        val items = state.value.items
        val index = event.index
        val currentCategory = findCategoryIndex(items, index.toString())
        updateState { it.copy(currentTab = index, currentCategory = currentCategory) }
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
        orderRepository.updateOrder(productQuantity, product)
    }

    private suspend fun loadMenu(menuId: String) {
        try {
            when (val response = getMenuUseCase(menuId)) {
                is GetMenuResponse.Success -> {
                    val categories = response.menu.categories
                    val products = response.products
                    cachedProducts.clear()
                    cachedProducts.addAll(products)

                    val orderedProducts = orderRepository.order.value
                    val items = obtainUiItems(categories, products, orderedProducts)

                    updateState {
                        it.copy(tabs = categories?.map { category -> category.name } ?: emptyList(), items = items)
                    }
                }
                is GetMenuResponse.Error -> {
                    updateState { it.copy(showError = SingleEvent(Unit)) }
                }
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(
        val tabs: List<String> = emptyList(),
        val currentTab: Int = 0,
        val items: List<UiItem> = emptyList(),
        val currentCategory: Int = 0,
        val showError: SingleEvent<Unit>? = null
    )

    sealed class Event {
        data class TabClicked(val index: Int) : Event()
        data class ProductClicked(val id: String) : Event()
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

fun obtainUiItems(
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<UiItem> {
    val items = mutableListOf<UiItem>()
    val matchedProducts = matchProductsWithOrderedProducts(products, orderedProducts)
    items.addAll(matchedProducts)
    return items
}

fun obtainUiItems(
    categories: List<Category>?,
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<UiItem> {
    val items = mutableListOf<UiItem>()
    val groupedProducts = products.groupBy { it.categoryId }.entries

    groupedProducts.forEach { (categoryId, products) ->
        val category = categories?.find { it.id == categoryId }
        if (category != null) items.add(CategoryMapper.map(category))

        val matchedProducts = matchProductsWithOrderedProducts(products, orderedProducts)
        items.addAll(matchedProducts)
    }
    return items
}

private fun matchProductsWithOrderedProducts(
    products: List<Product>,
    orderedProducts: List<OrderedProduct>
): List<ProductUiItem> = products.map { product ->
    val matchedProductWithOrderedProduct = orderedProducts
        .firstOrNull { orderedProduct -> orderedProduct.product.id == product.id }

    if (matchedProductWithOrderedProduct != null) {
        val orderedQuantity = matchedProductWithOrderedProduct.quantity
        ProductMapper.map(product, orderedQuantity)
    } else {
        ProductMapper.map(product)
    }
}

fun findCategoryIndex(items: List<UiItem>, index: String): Int = items.indexOfFirst { item ->
    if (item is CategoryUiItem) {
        item.id == index
    } else {
        false
    }
}