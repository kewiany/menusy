package xyz.kewiany.menusy.ui.menu.items

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.entity.Product
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.usecase.GetProductsResponse
import xyz.kewiany.menusy.usecase.GetProductsUseCase
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MenuItemsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel<State, Event>(State()) {

    init {
        loadProducts("menuId")
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.ProductClicked -> println(event.id)
    }

    private fun loadProducts(menuId: String) = viewModelScope.launch {
        try {
            when (val response = getProductsUseCase(menuId)) {
                is GetProductsResponse.Success -> {
                    updateState { it.copy(products = response.products) }
                }
                is GetProductsResponse.Error -> Unit
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(val products: List<Product> = emptyList())
    sealed class Event {
        data class ProductClicked(val id: String) : Event()
    }
}