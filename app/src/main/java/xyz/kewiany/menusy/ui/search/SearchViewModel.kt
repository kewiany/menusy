package xyz.kewiany.menusy.ui.search

import xyz.kewiany.menusy.ui.search.SearchViewModel.Event
import xyz.kewiany.menusy.ui.search.SearchViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel

class SearchViewModel : BaseViewModel<State, Event>(State) {

    override fun handleEvent(event: Event) {

    }

    object State
    object Event
}