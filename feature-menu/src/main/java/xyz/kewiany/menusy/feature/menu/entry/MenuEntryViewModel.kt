package xyz.kewiany.menusy.feature.menu.entry

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.kewiany.menusy.android.common.BaseViewModel
import xyz.kewiany.menusy.android.common.SingleEvent
import xyz.kewiany.menusy.android.common.navigation.NavigationDirections
import xyz.kewiany.menusy.android.common.navigation.Navigator
import xyz.kewiany.menusy.common.DispatcherProvider
import xyz.kewiany.menusy.common.Loggable
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.usecase.menu.GetPlaceUseCase
import xyz.kewiany.menusy.domain.usecase.menu.PlaceData
import xyz.kewiany.menusy.feature.menu.entry.MenuEntryViewModel.Event
import xyz.kewiany.menusy.feature.menu.entry.MenuEntryViewModel.State
import xyz.kewiany.menusy.model.Menu
import javax.inject.Inject

@HiltViewModel
class MenuEntryViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getPlaceUseCase: GetPlaceUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel<State, Event>(State()) {

    private val placeId = "1"

    override fun handleEvent(event: Event) = when (event) {
        is Event.TriggerLoadMenus -> handleLoadMenusTriggered()
        is Event.MenuClicked -> navigator.navigate(NavigationDirections.menuItems(event.id))
    }

    private fun handleLoadMenusTriggered() {
        viewModelScope.launch { loadPlace(placeId) }
    }

    private suspend fun loadPlace(placeId: String) {
        updateState { it.copy(showLoading = true) }
        when (val result = getPlaceUseCase(placeId)) {
            is Result.Success -> handlePlace(result.data)
            is Result.Error -> handleError()
        }
    }

    private fun handlePlace(data: PlaceData) {
        updateState {
            it.copy(
                name = data.place.name,
                address = data.place.address,
                menus = data.menus,
                showLoading = false
            )
        }
    }

    private fun handleError() {
        updateState { it.copy(showError = SingleEvent(Unit), showLoading = false) }
    }

    data class State(
        val name: String? = null,
        val address: String? = null,
        val menus: List<Menu> = emptyList(),
        val showLoading: Boolean = false,
        val showError: SingleEvent<Unit>? = null
    )

    sealed class Event {
        object TriggerLoadMenus : Event()
        data class MenuClicked(val id: String) : Event(), Loggable
    }
}