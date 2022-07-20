package xyz.kewiany.menusy.viewmodel

import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.common.navigation.NavigationDirections
import xyz.kewiany.menusy.common.navigation.Navigator
import xyz.kewiany.menusy.core.MainViewModel
import xyz.kewiany.menusy.core.MainViewModel.Event
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsCountUseCase
import xyz.kewiany.menusy.domain.usecase.search.ClearSearchTextUseCase
import xyz.kewiany.menusy.domain.usecase.search.SetSearchTextUseCase
import kotlin.random.Random

class MainViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val clearSearchTextUseCase = mockk<ClearSearchTextUseCase> {
        justRun { this@mockk.invoke() }
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
        getOrderedProductsCountUseCase,
        setSearchTextUseCase
    )

    @Test
    fun when_menuClicked_then_navigateToMenuEntryScreen() {
        viewModel().eventHandler(Event.MenuClicked)
        coVerify { navigator.navigate(NavigationDirections.menuEntry) }
    }

    @Test
    fun when_orderClicked_then_navigateToOrderScreen() {
        viewModel().eventHandler(Event.OrderClicked)
        coVerify { navigator.navigate(NavigationDirections.order) }
    }

    @Test
    fun when_historyClicked_then_navigateToHistoryScreen() {
        viewModel().eventHandler(Event.HistoryClicked)
        coVerify { navigator.navigate(NavigationDirections.history) }
    }

    @Test
    fun when_searchClicked_then_navigateToSearchScreen() {
        viewModel().eventHandler(Event.SearchClicked)
        coVerify { navigator.navigate(NavigationDirections.search) }
    }

    @Test
    fun when_changeLanguageClicked_then_navigateToChangeLanguageScreen() {
        viewModel().eventHandler(Event.ChangeLanguageClicked)
        coVerify { navigator.navigate(NavigationDirections.changeLanguage) }
    }

    @Test
    fun when_searchTextChanged_then_setSearchText() = testScope.runTest {
        val expectedSearchText = Random.nextLong().toString()
        val viewModel = viewModel()
        viewModel.eventHandler(Event.SearchTextChanged(expectedSearchText))

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