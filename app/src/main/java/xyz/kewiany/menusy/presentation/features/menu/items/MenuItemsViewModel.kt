package xyz.kewiany.menusy.presentation.features.menu.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.usecase.order.UpdateOrderUseCase
import xyz.kewiany.menusy.presentation.features.common.GetMenuContentFacade
import xyz.kewiany.menusy.presentation.features.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.presentation.features.menu.items.MenuItemsViewModel.State
import xyz.kewiany.menusy.presentation.utils.*

class MenuItemsViewModel @AssistedInject constructor(
    private val getMenuContentFacade: GetMenuContentFacade,
    private val updateOrderUseCase: UpdateOrderUseCase,
    @Assisted private val menuId: String,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        is Event.TriggerDismissError -> handleDismissErrorTriggered()
        is Event.ErrorOKClicked -> handleErrorOKClicked()
        is Event.TriggerLoadMenu -> handleLoadMenuTriggered()
        is Event.TabClicked -> handleTabClicked(event)
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

    private fun handleLoadMenuTriggered() {
        viewModelScope.launch { loadMenu(menuId) }
    }

    private fun handleTabClicked(event: Event.TabClicked) {
        val id = event.id
        val tabs = state.value.tabs
        val indexOnTabs = tabs.indexOfFirst { item -> item.id == id }
        val items = state.value.items
        val indexOnList = items.indexOfFirst { item -> if (item is CategoryUiItem) item.id == id else false }
        updateState { it.copy(currentTab = indexOnTabs, currentCategory = indexOnList) }
    }

    private fun handleProductClicked(event: Event.ProductClicked) {
        val productId = event.id
        val (quantity, items) = try {
            ProductUItemModifier.select(state.value.items, productId)
        } catch (e: ChangeQuantityException) {
            println(e)
            return
        }
        updateState { it.copy(items = items) }

        viewModelScope.launch { updateOrder(quantity, productId) }
    }

    private fun handleDecreaseQuantity(event: Event.DecreaseQuantityClicked) {
        val productId = event.productId
        val (quantity, items) = try {
            ProductUItemModifier.decreaseQuantity(state.value.items, productId)
        } catch (e: ChangeQuantityException) {
            println(e)
            return
        }
        updateState { it.copy(items = items) }

        viewModelScope.launch { updateOrder(quantity, productId) }
    }

    private fun handleIncreaseQuantity(event: Event.IncreaseQuantityClicked) {
        val productId = event.productId
        val (quantity, items) = try {
            ProductUItemModifier.increaseQuantity(state.value.items, productId)
        } catch (e: ChangeQuantityException) {
            println(e)
            return
        }
        updateState { it.copy(items = items) }

        viewModelScope.launch { updateOrder(quantity, productId) }
    }

    private suspend fun updateOrder(quantity: Int, productId: String) {
        try {
            updateOrderUseCase(quantity, productId)
        } catch (e: CancellationException) {
            println(e)
        }
    }

    private suspend fun loadMenu(menuId: String) {
        updateState { it.copy(showLoading = true) }
        when (val result = getMenuContentFacade.getContent(menuId)) {
            is Result.Success -> {
                val tabs = result.data.tabs
                val items = result.data.items
                updateState { it.copy(tabs = tabs, items = items, showLoading = false) }
            }
            is Result.Error -> {
                updateState { it.copy(showError = true, showLoading = false) }
            }
        }
    }

    data class State(
        val tabs: List<CategoryTab> = emptyList(),
        val currentTab: Int = 0,
        val items: List<UiItem> = emptyList(),
        val currentCategory: Int = 0,
        val showLoading: Boolean = false,
        val showError: Boolean = false
    )

    sealed class Event {
        object TriggerDismissError : Event()
        object ErrorOKClicked : Event()
        object TriggerLoadMenu : Event()
        data class TabClicked(val id: String) : Event(), Loggable
        data class ProductClicked(val id: String) : Event(), Loggable
        data class IncreaseQuantityClicked(val productId: String) : Event(), Loggable
        data class DecreaseQuantityClicked(val productId: String) : Event(), Loggable
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