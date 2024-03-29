package xyz.kewiany.menusy.feature.menu.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.android.common.BaseViewModel
import xyz.kewiany.menusy.android.common.CategoryUiItem
import xyz.kewiany.menusy.android.common.ProductQuantityModifier
import xyz.kewiany.menusy.android.common.ProductSelectionModifier
import xyz.kewiany.menusy.common.*
import xyz.kewiany.menusy.domain.usecase.order.UpdateOrderUseCase
import xyz.kewiany.menusy.feature.menu.GetMenuContentFacade
import xyz.kewiany.menusy.feature.menu.MenuContentData
import xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel.State

class MenuItemsViewModel @AssistedInject constructor(
    private val getMenuContentFacade: GetMenuContentFacade,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val productSelectionModifier: ProductSelectionModifier,
    private val productQuantityModifier: ProductQuantityModifier,
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
        val items = state.value.items
        val productId = event.id

        val (newQuantity, newItems) = productSelectionModifier.select(items, productId)
        updateState { it.copy(items = newItems) }

        viewModelScope.launch { updateOrder(newQuantity, productId) }
    }

    private fun handleDecreaseQuantity(event: Event.DecreaseQuantityClicked) {
        val productId = event.productId
        val items = state.value.items
        val (newQuantity, newItems) = productQuantityModifier.decreaseQuantity(items, productId) ?: return

        updateState { it.copy(items = newItems) }

        viewModelScope.launch { updateOrder(newQuantity, productId) }
    }

    private fun handleIncreaseQuantity(event: Event.IncreaseQuantityClicked) {
        val productId = event.productId
        val items = state.value.items
        val (newQuantity, newItems) = productQuantityModifier.increaseQuantity(items, productId) ?: return

        updateState { it.copy(items = newItems) }

        viewModelScope.launch { updateOrder(newQuantity, productId) }
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
            is Result.Success -> handleMenuContentData(result.data)
            is Result.Error -> handleError()
        }
    }

    private fun handleMenuContentData(data: MenuContentData) {
        val tabs = data.tabs
        val items = data.items
        updateState { it.copy(tabs = tabs, items = items, showLoading = false) }
    }

    private fun handleError() {
        updateState { it.copy(showError = true, showLoading = false) }
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