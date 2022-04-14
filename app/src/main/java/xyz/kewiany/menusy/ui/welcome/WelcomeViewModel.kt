package xyz.kewiany.menusy.ui.welcome

import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.Event
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.Event.ShowProgress
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class WelcomeViewModel : BaseViewModel<State, Event>(State()) {

    override fun handleEvent(event: Event) = when (event) {
        is ShowProgress -> handleShowProgress(event)
    }

    private fun handleShowProgress(event: ShowProgress) {
        updateState {
            it.copy(showProgress = event.show)
        }
    }

    data class State(
        val showProgress: Boolean = false
    )

    sealed class Event {
        data class ShowProgress(val show: Boolean) : Event()
    }
}