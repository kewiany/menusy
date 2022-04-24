package xyz.kewiany.menusy.ui.menu.entry

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.entity.Menu
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel.State
import xyz.kewiany.menusy.usecase.GetMenusResponse.Error
import xyz.kewiany.menusy.usecase.GetMenusResponse.Success
import xyz.kewiany.menusy.usecase.GetMenusUseCase
import xyz.kewiany.menusy.utils.BaseViewModel

class MenuEntryViewModel(
    private val navigator: Navigator,
    private val getMenusUseCase: GetMenusUseCase
) : BaseViewModel<State, Event>(State()) {

    init {
        loadMenus()
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.MenuClicked -> navigator.navigate(NavigationDirections.menuItems(event.id))
    }

    private fun loadMenus() = viewModelScope.launch {
        try {
            when (val response = getMenusUseCase()) {
                is Success -> {
                    updateState { it.copy(menus = response.menus) }
                }
                is Error -> Unit
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(val menus: List<Menu> = emptyList())
    sealed class Event {
        data class MenuClicked(val id: String) : Event()
    }
}