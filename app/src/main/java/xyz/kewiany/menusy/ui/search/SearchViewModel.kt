package xyz.kewiany.menusy.ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import xyz.kewiany.menusy.MenuRepository
import xyz.kewiany.menusy.OrderRepository
import xyz.kewiany.menusy.SearchTextHolder
import xyz.kewiany.menusy.ui.menu.items.obtainUiItems
import xyz.kewiany.menusy.ui.search.SearchViewModel.Event
import xyz.kewiany.menusy.ui.search.SearchViewModel.State
import xyz.kewiany.menusy.utils.BaseViewModel
import xyz.kewiany.menusy.utils.UiItem
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTextHolder: SearchTextHolder,
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel<State, Event>(State()) {

    init {
        searchTextFlow().launchIn(viewModelScope)
    }

    override fun handleEvent(event: Event) = when (event) {
        is Event.SearchItemClicked -> handleSearchItemClicked(event)
    }

    private fun handleSearchItemClicked(event: Event.SearchItemClicked) {
        println(event.id)
    }

    private fun searchTextFlow() = searchTextHolder.searchText
        .onStart { emit("") }
        .debounce(500L)
        .onEach { text -> if (text.isNotEmpty()) queryProducts(text) }

    private suspend fun queryProducts(query: String) {
        val products = menuRepository.getProductsByQuery(query)
        val orderedProducts = orderRepository.order.value
        val items = obtainUiItems(products, orderedProducts)

        updateState {
            it.copy(results = items)
        }
    }

    data class State(
        val results: List<UiItem> = emptyList()
    )

    sealed class Event {
        data class SearchItemClicked(val id: String) : Event()
    }
}