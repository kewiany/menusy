package xyz.kewiany.menusy.ui.menu.entry

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import xyz.kewiany.menusy.utils.DispatcherProvider
import xyz.kewiany.menusy.utils.SingleEvent
import javax.inject.Inject

@HiltViewModel
class MenuEntryViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getMenusUseCase: GetMenusUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    init {
        viewModelScope.launch(dispatcherProvider.main()) {
            loadMenus()
        }
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.MenuClicked -> navigator.navigate(NavigationDirections.menuItems(event.id))
    }

    private suspend fun loadMenus() {
        try {
            updateState { it.copy(showLoading = true) }
            when (val response = getMenusUseCase()) {
                is Success -> {
                    updateState { it.copy(menus = response.menus, showLoading = false) }
                }
                is Error -> {
                    updateState { it.copy(showError = SingleEvent(Unit), showLoading = false) }
                }
            }
        } catch (e: CancellationException) {
            println(e)
        }
    }

    data class State(
        val menus: List<Menu> = emptyList(),
        val showLoading: Boolean = false,
        val showError: SingleEvent<Unit>? = null
    )

    sealed class Event {
        data class MenuClicked(val id: String) : Event()
    }
}