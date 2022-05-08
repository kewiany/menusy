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
    private val navigator: Navigator,
    settingsRepository: SettingsRepository
) : BaseViewModel<State, Event>(State) {

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
    }

    object State
    sealed class Event {
        object MenuClicked : Event()
        object OrderClicked : Event()
        object SearchClicked : Event()
        object ChangeLanguageClicked : Event()
    }
}