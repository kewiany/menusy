package xyz.kewiany.menusy.ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import xyz.kewiany.menusy.SearchTextHolder
import xyz.kewiany.menusy.ui.search.SearchViewModel.Event
import xyz.kewiany.menusy.ui.search.SearchViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchTextHolder: SearchTextHolder
) : BaseViewModel<State, Event>(State()) {

    private val results = mutableListOf<String>()

    init {
        searchTextHolder.searchText
            .onStart { emit("") }
            .debounce(500L)
            .onEach { text ->
                if (text.isNotEmpty()) {
                    results.add(text)
                    updateState { it.copy(results = results.toList()) }
                }
            }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: Event) {

    }

    data class State(
        val results: List<String> = emptyList()
    )

    object Event
}