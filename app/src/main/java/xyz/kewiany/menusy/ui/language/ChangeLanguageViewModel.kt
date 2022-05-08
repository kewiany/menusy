package xyz.kewiany.menusy.ui.language

import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.Event
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    private val navigator: Navigator,
) : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) = when (event) {
        is Event.OutsideClicked -> {
            navigator.back()
        }
        is Event.CancelClicked -> {
            navigator.back()
        }
        is Event.OKClicked -> {
            navigator.back()
        }
    }

    object State
    sealed class Event {
        object OutsideClicked : Event()
        object CancelClicked : Event()
        object OKClicked : Event()
    }
}
