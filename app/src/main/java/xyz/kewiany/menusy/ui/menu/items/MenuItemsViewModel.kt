package xyz.kewiany.menusy.ui.menu.items

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.usecase.GetMenuResponse
import xyz.kewiany.menusy.usecase.GetMenuUseCase
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.UiItem
import javax.inject.Inject

@HiltViewModel
class MenuItemsViewModel @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
) : BaseViewModel<State, Event>(State()) {

    init {
        loadMenu("1")
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.ProductClicked -> println(event.id)
    }

    private fun loadMenu(menuId: String) = viewModelScope.launch {
        try {
            when (val response = getMenuUseCase(menuId)) {
                is GetMenuResponse.Success -> {
                    val items = mutableListOf<UiItem>()
                    response.products
                        .groupBy { it.categoryId }.entries
                        .forEach { (categoryId, products) ->
                            val category = response.menu.categories?.last { it.id == categoryId }
                            if (category != null) {
                                items.add(CategoryUiItem(category.id, category.name))
                            }
                            items.addAll(products.map { product ->
                                ProductUiItem(
                                    product.id,
                                    product.name,
                                    product.description,
                                    product.price
                                )
                            })
                        }
                    updateState { it.copy(items = items) }
                }
                is GetMenuResponse.Error -> Unit
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(val items: List<UiItem> = emptyList())
    sealed class Event {
        data class ProductClicked(val id: String) : Event()
    }
}