package xyz.kewiany.menusy

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import xyz.kewiany.menusy.ui.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.ui.menu.items.combineToMenu
import xyz.kewiany.menusy.ui.menu.items.findCategoryIndex
import xyz.kewiany.menusy.usecase.GetMenuResponse
import xyz.kewiany.menusy.usecase.GetMenuUseCase

class MenuItemsViewModelTest : BaseTest() {

    private val menuId: String = "id"
    private val menu = createMenu()
    private val products = listOf(
        createProduct(),
        createProduct(),
        createProduct()
    )
    private val uiItems = combineToMenu(menu.categories, products)
    private val getMenuUseCase = mockk<GetMenuUseCase> {
        coEvery { this@mockk.invoke(menuId) } returns GetMenuResponse.Success(menu, products)
    }

    private fun viewModel() = MenuItemsViewModel(
        getMenuUseCase,
        menuId,
        testDispatcherProvider
    )

    @Test
    fun given_loadMenu_then_getMenu() = testScope.runTest {
        viewModel().state.test {
            skipItems(1); awaitItem()
            coVerify { getMenuUseCase(menuId) }
        }
    }

    @Test
    fun given_loadMenu_when_getMenuSuccessful_then_showProducts() = testScope.runTest {
        viewModel().state.test {
            skipItems(1)
            assert(awaitItem().items == uiItems)
        }
    }

    @Test
    fun given_loadMenu_when_getMenuSuccessful_then_showTabs() = testScope.runTest {
        viewModel().state.test {
            skipItems(1)
            assert(awaitItem().tabs == (menu.categories?.map { it.name } ?: emptyList<String>()))
        }
    }

    @Test
    fun when_tabClicked_then_showTab() = testScope.runTest {
        val index = 2
        val viewModel = viewModel()
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(MenuItemsViewModel.Event.TabClicked(index))
            assert(awaitItem().currentTab == index)
        }
    }

    @Test
    fun when_tabClicked_then_showCategory() = testScope.runTest {
        val index = 2
        val viewModel = viewModel()
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(MenuItemsViewModel.Event.TabClicked(index))
            assert(awaitItem().currentCategory == findCategoryIndex(uiItems, index.toString()))
        }
    }
}