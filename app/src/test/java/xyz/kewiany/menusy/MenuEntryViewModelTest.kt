package xyz.kewiany.menusy

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import xyz.kewiany.menusy.navigation.Navigator
import xyz.kewiany.menusy.ui.menu.entry.MenuEntryViewModel
import xyz.kewiany.menusy.usecase.GetMenusResponse
import xyz.kewiany.menusy.usecase.GetMenusUseCase

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
            assert(awaitItem().menus == menus)
        }
    }

    @Test
    fun when_menuClicked_then_navigateToMenuItems() {
        val id = "id"
        viewModel.eventHandler(MenuEntryViewModel.Event.MenuClicked(id))
        coVerify { navigator.navigate(any()) }
    }
}