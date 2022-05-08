package xyz.kewiany.menusy.ui.language

import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.Event
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class ChangeLanguageViewModel : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}
