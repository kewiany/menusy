package xyz.kewiany.menusy.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.SearchTextHolder
import xyz.kewiany.menusy.SettingsRepository
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.MainViewModel
import xyz.kewiany.menusy.ui.language.Language
import kotlin.random.Random

class MainViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val searchTextHolder = mockk<SearchTextHolder> {
        justRun { this@mockk.setSearchText(any()) }
    }
    private val settingsRepository = mockk<SettingsRepository> {
        coEvery { this@mockk.language } returns MutableStateFlow(Language.ENGLISH)
    }

    private val viewModel: MainViewModel by lazy {
        MainViewModel(
            navigator,
            searchTextHolder,
            settingsRepository
        )
    }

    @Test
    fun when_menuClicked_then_navigate_to_menuEntryScreen() {
        viewModel.eventHandler(MainViewModel.Event.MenuClicked)
        coVerify { navigator.navigate(NavigationDirections.menuEntry) }
    }

    @Test
    fun when_orderClicked_then_navigate_to_orderScreen() {
        viewModel.eventHandler(MainViewModel.Event.OrderClicked)
        coVerify { navigator.navigate(NavigationDirections.order) }
    }

    @Test
    fun when_searchClicked_then_navigate_to_searchScreen() {
        viewModel.eventHandler(MainViewModel.Event.SearchClicked)
        coVerify { navigator.navigate(NavigationDirections.search) }
    }

    @Test
    fun when_changeLanguageClicked_then_navigate_to_changeLanguageScreen() {
        viewModel.eventHandler(MainViewModel.Event.ChangeLanguageClicked)
        coVerify { navigator.navigate(NavigationDirections.changeLanguage) }
    }

    @Test
    fun when_clearSearchClicked_then_setEmptySearchText() {
        viewModel.eventHandler(MainViewModel.Event.ClearSearchClicked)

        assertEquals("", viewModel.state.value.searchText)
    }

    @Test
    fun when_setCurrentRoute_then_setCurrentRoute() {
        val route = NavigationDirections.welcome.destination
        viewModel.eventHandler(MainViewModel.Event.SetCurrentRoute(route))

        assertEquals(route, viewModel.state.value.currentRoute)
    }

    @Test
    fun when_searchTextChanged_then_setSearchText() {
        val searchText = Random.nextLong().toString()
        viewModel.eventHandler(MainViewModel.Event.SearchTextChanged(searchText))

        assertEquals(searchText, viewModel.state.value.searchText)
    }

    @Test
    fun when_searchFocused_then_changeClearButtonVisible() {
        val isFocused = true
        viewModel.eventHandler(MainViewModel.Event.SearchFocused(isFocused))

        assertEquals(isFocused, viewModel.state.value.showClearButton)
    }

//    Event.ClearSearchClicked -> handleClearSearchClicked()
//    is Event.SetCurrentRoute -> handleCurrentRoute(event)
//    is Event.SearchTextChanged -> handleSearchTextChanged(event)
//    is Event.SearchFocused -> handleSearchFocused(event)
}