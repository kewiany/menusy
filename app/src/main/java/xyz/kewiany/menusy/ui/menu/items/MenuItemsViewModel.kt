package xyz.kewiany.menusy.ui.menu.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.usecase.GetMenuResponse
import xyz.kewiany.menusy.usecase.GetMenuUseCase
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.UiItem

class MenuItemsViewModel @AssistedInject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    @Assisted private val menuId: String,
) : BaseViewModel<State, Event>(State()) {

    init {
        loadMenu(menuId)
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.TabClicked -> {
            updateState { it.copy(currentTab = event.index) }
        }
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
                            val category = response.menu.categories?.find { it.id == categoryId }
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
                    updateState {
                        it.copy(
                            tabs = response.menu.categories?.map { category -> category.name } ?: emptyList(),
                            items = items
                        )
                    }
                }
                is GetMenuResponse.Error -> Unit
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(
        val tabs: List<String> = emptyList(),
        val currentTab: Int = 0,
        val items: List<UiItem> = emptyList()
    )

    sealed class Event {
        data class TabClicked(val index: Int) : Event()
        data class ProductClicked(val id: String) : Event()
    }

    @AssistedFactory
    interface Factory {
        fun create(menuId: String): MenuItemsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            menuId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(menuId) as T
            }
        }
    }
}