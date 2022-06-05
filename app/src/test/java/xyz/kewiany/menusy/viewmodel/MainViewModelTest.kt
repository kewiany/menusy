package xyz.kewiany.menusy.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.SearchTextHolder
import xyz.kewiany.menusy.SettingsRepository
import xyz.kewiany.menusy.navigation.NavigationDirections
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.MainViewModel
import xyz.kewiany.menusy.ui.language.Language

class MainViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val searchTextHolder = mockk<SearchTextHolder>()
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
}