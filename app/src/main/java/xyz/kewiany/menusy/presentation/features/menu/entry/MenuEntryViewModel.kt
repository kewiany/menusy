package xyz.kewiany.menusy.presentation.features.menu.entry

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.common.navigation.NavigationDirections
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.usecase.menu.GetMenusUseCase
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import xyz.kewiany.menusy.presentation.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class MenuEntryViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getMenusUseCase: GetMenusUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        is Event.TriggerLoadMenus -> handleLoadMenusTriggered()
        is Event.MenuClicked -> navigator.navigate(NavigationDirections.menuItems(event.id))
    }

    private fun handleLoadMenusTriggered() {
        viewModelScope.launch { loadMenus() }
    }

    private suspend fun loadMenus() {
        updateState { it.copy(showLoading = true) }
        when (val result = getMenusUseCase()) {
            is Result.Success -> {
                updateState { it.copy(menus = result.data, showLoading = false) }
            }
            is Result.Error -> {
                updateState { it.copy(showError = SingleEvent(Unit), showLoading = false) }
            }
        }
    }

    data class State(
        val menus: List<Menu> = emptyList(),
        val showLoading: Boolean = false,
        val showError: SingleEvent<Unit>? = null
    )

    sealed class Event {
        object TriggerLoadMenus : Event()
        data class MenuClicked(val id: String) : Event()
    }
}