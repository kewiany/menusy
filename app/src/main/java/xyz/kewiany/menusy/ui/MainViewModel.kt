package xyz.kewiany.menusy.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.SearchRepository
import xyz.kewiany.menusy.SettingsRepository
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.MainViewModel.Event
import xyz.kewiany.menusy.ui.MainViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator,
    private val searchRepository: SearchRepository,
    private val orderRepository: OrderRepository,
    private val settingsRepository: SettingsRepository
) : BaseViewModel<State, Event>(State()) {

    init {
        languageFlow().launchIn(viewModelScope)
        productsOrderedCountFlow().launchIn(viewModelScope)
        searchTextFlow().launchIn(viewModelScope)
    }

    private fun languageFlow() = settingsRepository.language
        .onEach { language -> println("language $language") }

    private fun productsOrderedCountFlow() = orderRepository.orderedProductsCount
        .onEach { count -> updateState { it.copy(orderedProductsCount = count) } }

    private fun searchTextFlow() = searchRepository.searchText
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
        val showBackButton = currentRoute == NavigationDirections.search.destination
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
        searchRepository.clearSearchText()
    }

    private fun handleSearchTextChanged(event: Event.SearchTextChanged) {
        val text = event.text
        searchRepository.setSearchText(text)
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
        data class SetCurrentRoute(val route: String?) : Event()
        data class SearchFocused(val isFocused: Boolean) : Event()
        data class SearchTextChanged(val text: String) : Event()
    }
}