package xyz.kewiany.menusy.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import xyz.kewiany.menusy.SettingsRepository
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.MainViewModel.Event
import xyz.kewiany.menusy.ui.MainViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator, settingsRepository: SettingsRepository
) : BaseViewModel<State, Event>(State()) {

    init {
        settingsRepository.language.onEach {
            println("language $it")
        }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: Event) = when (event) {
        Event.MenuClicked -> navigator.navigate(NavigationDirections.menuEntry)
        Event.OrderClicked -> navigator.navigate(NavigationDirections.order)
        Event.SearchClicked -> navigator.navigate(NavigationDirections.search)
        Event.ChangeLanguageClicked -> navigator.navigate(NavigationDirections.changeLanguage)
        Event.ClearSearchClicked -> handleClearSearchClicked()
        is Event.SearchTextChanged -> handleSearchTextChanged(event)
        is Event.SearchFocused -> handleSearchFocused(event)
    }

    private fun handleClearSearchClicked() {
        updateState { it.copy(searchText = "") }
    }

    private fun handleSearchTextChanged(event: Event.SearchTextChanged) {
        updateState { it.copy(searchText = event.text) }
    }

    private fun handleSearchFocused(event: Event.SearchFocused) {
        updateState { it.copy(showClearButton = event.isFocused) }
    }

    data class State(
        val searchText: String = "",
        val showClearButton: Boolean = false
    )

    sealed class Event {
        object MenuClicked : Event()
        object OrderClicked : Event()
        object SearchClicked : Event()
        object ChangeLanguageClicked : Event()
        object ClearSearchClicked : Event()
        data class SearchFocused(val isFocused: Boolean) : Event()
        data class SearchTextChanged(val text: String) : Event()
    }
}