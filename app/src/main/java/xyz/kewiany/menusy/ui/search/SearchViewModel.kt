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
import kotlin.random.Random

@HiltViewModel
class SearchViewModel @Inject constructor(
    searchTextHolder: SearchTextHolder
) : BaseViewModel<State, Event>(State()) {

    init {
        searchTextHolder.searchText
            .onStart { emit("") }
            .debounce(500L)
            .onEach { text ->
                val results = state.value.results.toMutableList()
                if (text.isNotEmpty()) {
                    results.add(SearchUiItem(Random.nextInt().toString(), text))
                    updateState { it.copy(results = results.toList()) }
                }
            }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.SearchItemClicked -> handleSearchItemClicked(event)
    }

    private fun handleSearchItemClicked(event: Event.SearchItemClicked) {
        println(event.id)
    }

    data class State(
        val results: List<SearchUiItem> = emptyList()
    )

    sealed class Event {
        data class SearchItemClicked(val id: String) : Event()
    }
}