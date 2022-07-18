package xyz.kewiany.menusy.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.createMenu
import xyz.kewiany.menusy.domain.usecase.GetMenusError
import xyz.kewiany.menusy.domain.usecase.GetMenusResponse
import xyz.kewiany.menusy.domain.usecase.GetMenusUseCase
import xyz.kewiany.menusy.presentation.features.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.presentation.navigation.Navigator

class MenuEntryViewModelTest : BaseTest() {

    private val navigator = mockk<Navigator>(relaxed = true)
    private val menus = listOf(createMenu(), createMenu())
    private val getMenusUseCase = mockk<GetMenusUseCase> {
        coEvery { this@mockk.invoke() } returns GetMenusResponse.Success(menus)
    }

    private val viewModel: MenuEntryViewModel by lazy {
        MenuEntryViewModel(
            navigator,
            getMenusUseCase,
            testDispatcherProvider
        )
    }

    @Test
    fun given_loadMenus_then_getMenus() = testScope.runTest {
        viewModel.state.test {
            skipItems(1); awaitItem()
            coVerify { getMenusUseCase() }
        }
    }

    @Test
    fun given_loadMenus_when_getMenusSuccessful_then_showMenus() = testScope.runTest {
        viewModel.state.test {
            skipItems(1)
            assertEquals(menus, awaitItem().menus)
        }
    }

    @Test
    fun given_loadMenus_when_getMenusFails_then_showError() = testScope.runTest {
        coEvery { getMenusUseCase.invoke() } returns GetMenusResponse.Error(GetMenusError.Unknown)
        viewModel.state.test {
            skipItems(1)
            assertNotNull(awaitItem().showError)
        }
    }

    @Test
    fun when_menuClicked_then_navigateToMenuItems() {
        val id = "id"
        viewModel.eventHandler(MenuEntryViewModel.Event.MenuClicked(id))
        coVerify { navigator.navigate(any()) }
    }
}