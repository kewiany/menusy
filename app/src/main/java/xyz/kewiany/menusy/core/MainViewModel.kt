package xyz.kewiany.menusy.core

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.common.navigation.NavigationDirections
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.core.MainViewModel.Event
import xyz.kewiany.menusy.core.MainViewModel.State
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsCountUseCase
import xyz.kewiany.menusy.domain.usecase.search.ClearSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.GetSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.SetSearchTextUseCase
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import xyz.kewiany.menusy.presentation.utils.Loggable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator,
    private val clearSearchTextUseCase: ClearSearchTextUseCase,
    private val getSearchTextUseCase: GetSearchTextUseCase,
    private val getOrderedProductsCountUseCase: GetOrderedProductsCountUseCase,
    private val setSearchTextUseCase: SetSearchTextUseCase,
) : BaseViewModel<State, Event>(State()) {

    init {
        productsOrderedCountFlow().launchIn(viewModelScope)
        searchTextFlow().launchIn(viewModelScope)
    }

    private fun productsOrderedCountFlow() = getOrderedProductsCountUseCase()
        .onEach { count -> updateState { it.copy(orderedProductsCount = count) } }

    private fun searchTextFlow() = getSearchTextUseCase()
        .onEach { text -> updateState { it.copy(searchText = text) } }

    override fun handleEvent(event: Event) = when (event) {
        Event.MenuClicked -> navigator.navigate(NavigationDirections.menuEntry)
        Event.OrderClicked -> navigator.navigate(NavigationDirections.order)
        Event.HistoryClicked -> navigator.navigate(NavigationDirections.history)
        Event.SearchClicked -> navigator.navigate(NavigationDirections.search)
        Event.ChangeLanguageClicked -> navigator.navigate(NavigationDirections.changeLanguage)
        Event.ClearSearchClicked -> handleClearSearchClicked()
        is Event.SetCurrentRoute -> handleCurrentRoute(event)
        is Event.SearchTextChanged -> handleSearchTextChanged(event)
        is Event.SearchFocused -> handleSearchFocused(event)
    }

    private fun handleCurrentRoute(event: Event.SetCurrentRoute) {
        val currentRoute = event.route
        val showBackButton = currentRoute == "menuEntry/{menuId}"
                || currentRoute == NavigationDirections.search.destination
                || currentRoute == NavigationDirections.history.destination
        val showBottomBar = currentRoute != NavigationDirections.welcome.destination
                && currentRoute != NavigationDirections.search.destination
        val showSearchButton = currentRoute != NavigationDirections.search.destination
                && currentRoute != NavigationDirections.welcome.destination
        val showSearchBar = currentRoute == NavigationDirections.search.destination
        updateState {
            it.copy(
                currentRoute = currentRoute,
                showBackButton = showBackButton,
                showBottomBar = showBottomBar,
                showSearchButton = showSearchButton,
                showSearchBar = showSearchBar
            )
        }
    }

    private fun handleClearSearchClicked() {
        clearSearchTextUseCase()
    }

    private fun handleSearchTextChanged(event: Event.SearchTextChanged) {
        val text = event.text
        setSearchTextUseCase(text)
    }

    private fun handleSearchFocused(event: Event.SearchFocused) {
        updateState { it.copy(showClearButton = event.isFocused) }
    }

    data class State(
        val currentRoute: String? = null,
        val showBackButton: Boolean = false,
        val showBottomBar: Boolean = false,
        val showSearchButton: Boolean = false,
        val showSearchBar: Boolean = false,
        val searchText: String = "",
        val showClearButton: Boolean = false,
        val orderedProductsCount: Int = 0
    )

    sealed class Event {
        object MenuClicked : Event()
        object OrderClicked : Event()
        object HistoryClicked : Event()
        object SearchClicked : Event()
        object ChangeLanguageClicked : Event()
        object ClearSearchClicked : Event()
        data class SetCurrentRoute(val route: String?) : Event(), Loggable
        data class SearchFocused(val isFocused: Boolean) : Event(), Loggable
        data class SearchTextChanged(val text: String) : Event(), Loggable
    }
}