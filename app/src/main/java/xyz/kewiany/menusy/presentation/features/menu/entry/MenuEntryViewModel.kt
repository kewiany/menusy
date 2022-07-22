package xyz.kewiany.menusy.presentation.features.menu.entry

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
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
            when (val result = getMenusUseCase()) {
                is Result.Success -> {
                    updateState { it.copy(menus = result.data, showLoading = false) }
                }
                is Result.Error -> {
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