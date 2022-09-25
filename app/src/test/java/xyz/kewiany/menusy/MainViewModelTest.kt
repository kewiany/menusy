package xyz.kewiany.menusy

import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.MainViewModel.Event
import xyz.kewiany.menusy.android.common.navigation.NavigationDirections
import xyz.kewiany.menusy.android.common.navigation.Navigator
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsCountUseCase
import xyz.kewiany.menusy.domain.usecase.search.ClearSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.GetSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.SetSearchTextUseCase
import xyz.kewiany.menusy.test.common.BaseTest
import kotlin.random.Random

class MainViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val clearSearchTextUseCase = mockk<ClearSearchTextUseCase> {
        justRun { this@mockk.invoke() }
    }
    private val searchTextFlow = MutableStateFlow("")
    private val getSearchTextUseCase = mockk<GetSearchTextUseCase> {
        every { this@mockk.invoke() } returns searchTextFlow
    }
    private val orderedProductsFlow = emptyFlow<Int>()
    private val getOrderedProductsCountUseCase = mockk<GetOrderedProductsCountUseCase> {
        every { this@mockk.invoke() } returns orderedProductsFlow
    }
    private val setSearchTextUseCase = mockk<SetSearchTextUseCase> {
        justRun { this@mockk.invoke(any()) }
    }

    private fun viewModel() = MainViewModel(
        navigator,
        clearSearchTextUseCase,
        getSearchTextUseCase,
        getOrderedProductsCountUseCase,
        setSearchTextUseCase
    )

    @Test
    fun when_menuClicked_then_navigateToMenuEntryScreen() {
        viewModel().eventHandler(Event.MenuClicked)
        coVerify { navigator.navigate(NavigationDirections.menuEntry) }
    }

    @Test
    fun when_currentRouteMenuEntry_then_doNotShowBackButton_and_showBottomBar_and_showSearchButton_and_doNotShowSearchBar() {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SetCurrentRoute(NavigationDirections.menuEntry.destination))

        val state = viewModel.state.value
        val actualShowBackButton = state.showBackButton
        val actualShowBottomBar = state.showBottomBar
        val actualShowSearchButton = state.showSearchButton
        val actualShowSearchBar = state.showSearchBar
        assertFalse(actualShowBackButton)
        assertTrue(actualShowBottomBar)
        assertTrue(actualShowSearchButton)
        assertFalse(actualShowSearchBar)
    }

    @Test
    fun when_orderClicked_then_navigateToOrderScreen() {
        viewModel().eventHandler(Event.OrderClicked)
        coVerify { navigator.navigate(NavigationDirections.order) }
    }

    @Test
    fun when_currentRouteOrder_then_doNotShowBackButton_and_showBottomBar_and_showSearchButton_and_doNotShowSearchBar() {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SetCurrentRoute(NavigationDirections.order.destination))

        val state = viewModel.state.value
        val actualShowBackButton = state.showBackButton
        val actualShowBottomBar = state.showBottomBar
        val actualShowSearchButton = state.showSearchButton
        val actualShowSearchBar = state.showSearchBar
        assertFalse(actualShowBackButton)
        assertTrue(actualShowBottomBar)
        assertTrue(actualShowSearchButton)
        assertFalse(actualShowSearchBar)
    }

    @Test
    fun when_historyClicked_then_navigateToHistoryScreen() {
        viewModel().eventHandler(Event.HistoryClicked)
        coVerify { navigator.navigate(NavigationDirections.history) }
    }

    @Test
    fun when_currentRouteHistory_then_showBackButton_and_showBottomBar_and_showSearchButton_and_doNotShowSearchBar() {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SetCurrentRoute(NavigationDirections.history.destination))

        val state = viewModel.state.value
        val actualShowBackButton = state.showBackButton
        val actualShowBottomBar = state.showBottomBar
        val actualShowSearchButton = state.showSearchButton
        val actualShowSearchBar = state.showSearchBar
        assertTrue(actualShowBackButton)
        assertTrue(actualShowBottomBar)
        assertTrue(actualShowSearchButton)
        assertFalse(actualShowSearchBar)
    }

    @Test
    fun when_searchClicked_then_navigateToSearchScreen() {
        viewModel().eventHandler(Event.SearchClicked)
        coVerify { navigator.navigate(NavigationDirections.search) }
    }

    @Test
    fun when_currentRouteSearch_then_showBackButton_and_doNotShwBottomBar_and_doNotShowSearchButton_and_howSearchBar() {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SetCurrentRoute(NavigationDirections.search.destination))

        val state = viewModel.state.value
        val actualShowBackButton = state.showBackButton
        val actualShowBottomBar = state.showBottomBar
        val actualShowSearchButton = state.showSearchButton
        val actualShowSearchBar = state.showSearchBar
        assertTrue(actualShowBackButton)
        assertFalse(actualShowBottomBar)
        assertFalse(actualShowSearchButton)
        assertTrue(actualShowSearchBar)
    }

    @Test
    fun when_changeLanguageClicked_then_navigateToChangeLanguageScreen() {
        viewModel().eventHandler(Event.ChangeLanguageClicked)
        coVerify { navigator.navigate(NavigationDirections.changeLanguage) }
    }

    @Test
    fun when_currentRouteChangeLanguage_then_doNotShowBackButton_and_showBottomBar_and_showSearchButton_and_doNotShowSearchBar() {
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SetCurrentRoute(NavigationDirections.changeLanguage.destination))

        val state = viewModel.state.value
        val actualShowBackButton = state.showBackButton
        val actualShowBottomBar = state.showBottomBar
        val actualShowSearchButton = state.showSearchButton
        val actualShowSearchBar = state.showSearchBar
        assertFalse(actualShowBackButton)
        assertTrue(actualShowBottomBar)
        assertTrue(actualShowSearchButton)
        assertFalse(actualShowSearchBar)
    }

    @Test
    fun when_searchTextChanged_then_setSearchText() = testScope.runTest {
        val expectedSearchText = Random.nextLong().toString()
        val viewModel = viewModel()
        searchTextFlow.value = expectedSearchText
        runCurrent()

        val actualSearchText = viewModel.state.value.searchText
        assertEquals(expectedSearchText, actualSearchText)
    }

    @Test
    fun when_clearSearchClicked_then_setEmptySearchText() {
        val expectedSearchText = ""
        val viewModel = viewModel()
        viewModel.eventHandler(Event.ClearSearchClicked)

        val actualSearchText = viewModel.state.value.searchText
        assertEquals(expectedSearchText, actualSearchText)
    }

    @Test
    fun when_setCurrentRoute_then_setCurrentRoute() {
        val expectedRoute = NavigationDirections.welcome.destination
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SetCurrentRoute(expectedRoute))

        val actualCurrentRoute = viewModel.state.value.currentRoute
        assertEquals(expectedRoute, actualCurrentRoute)
    }


    @Test
    fun when_searchFocused_then_changeClearButtonVisible() {
        val expectedShowClearButton = true
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SearchFocused(expectedShowClearButton))

        val actualShowClearButton = viewModel.state.value.showClearButton
        assertEquals(expectedShowClearButton, actualShowClearButton)
    }
}