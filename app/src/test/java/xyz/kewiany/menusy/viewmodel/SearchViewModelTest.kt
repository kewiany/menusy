package xyz.kewiany.menusy.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.ui.search.SearchTextHolder
import xyz.kewiany.menusy.ui.search.SearchViewModel
import kotlin.random.Random

class SearchViewModelTest : BaseTest() {

    private val text = Random.nextLong().toString()
    private val _searchText = MutableStateFlow("")
    private val searchTextHolder = mockk<SearchTextHolder> {
        coEvery { this@mockk.searchText } returns _searchText
    }

    private val viewModel: SearchViewModel by lazy {
        SearchViewModel(
            searchTextHolder
        )
    }

    @Test
    fun given_noSearchText_then_doNotResults() = testScope.runTest {
        val state = viewModel.state
        advanceTimeBy(600)
        assertEquals(emptyList<String>(), state.value.results)
    }

    @Test
    fun when_searchText_then_setResults() = testScope.runTest {
        val state = viewModel.state
        val item = SearchUiItem(Random.nextInt().toString(), text)
        _searchText.tryEmit(text)
        advanceTimeBy(600)
        assertTrue(state.value.results.map { it.name }.contains(item.name))
    }

    @Test
    fun when_searchTextTwice_then_setResultsOnce() = testScope.runTest {
        val state = viewModel.state
        _searchText.tryEmit(text)
        advanceTimeBy(600)
        val secondText = Random.nextLong().toString()
        _searchText.tryEmit(secondText)
        advanceTimeBy(400)
        assertFalse(state.value.results.map { it.name }.contains(secondText))
    }

    @Test
    fun when_searchTextTwice_then_setResultsTwice() = testScope.runTest {
        val state = viewModel.state
        _searchText.tryEmit(text)
        advanceTimeBy(600)
        val secondText = Random.nextLong().toString()
        _searchText.tryEmit(secondText)
        advanceTimeBy(600)
        assertTrue(state.value.results.map { it.name }.contains(secondText))
    }
}