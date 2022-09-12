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
import xyz.kewiany.menusy.domain.usecase.menu.GetPlaceUseCase
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel.State
import xyz.kewiany.menusy.presentation.utils.BaseViewModel
import xyz.kewiany.menusy.presentation.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class MenuEntryViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getPlaceUseCase: GetPlaceUseCase,
    private val getMenusUseCase: GetMenusUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    private val placeId = "1"

    override fun handleEvent(event: Event) = when (event) {
        is Event.TriggerLoadMenus -> handleLoadMenusTriggered()
        is Event.MenuClicked -> navigator.navigate(NavigationDirections.menuItems(event.id))
    }

    private fun handleLoadMenusTriggered() {
        viewModelScope.launch { loadPlace(placeId) }
        viewModelScope.launch { loadMenus(placeId) }
    }

    private suspend fun loadPlace(placeId: String) {
        updateState { it.copy(showLoading = true) }
        when (val result = getPlaceUseCase(placeId)) {
            is Result.Success -> {
                println("place ${result.data}")
            }
            is Result.Error -> {
                updateState { it.copy(showError = SingleEvent(Unit), showLoading = false) }
            }
        }
    }

    private suspend fun loadMenus(placeId: String) {
        updateState { it.copy(showLoading = true) }
        when (val result = getMenusUseCase(placeId)) {
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