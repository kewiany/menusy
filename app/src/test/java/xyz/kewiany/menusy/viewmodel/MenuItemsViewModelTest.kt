package xyz.kewiany.menusy.viewmodel

import app.cash.turbine.test
import io.mockk.*
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.createMenu
import xyz.kewiany.menusy.createProduct
import xyz.kewiany.menusy.data.repository.OrderRepository
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuError
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuResponse
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuUseCase
import xyz.kewiany.menusy.presentation.features.menu.items.MenuItemsViewModel
import xyz.kewiany.menusy.presentation.features.menu.items.MenuItemsViewModel.Event
import xyz.kewiany.menusy.presentation.utils.ProductUiItem
import xyz.kewiany.menusy.ui.menu.items.findCategoryId
import xyz.kewiany.menusy.ui.menu.items.obtainUiItems

class MenuItemsViewModelTest : BaseTest() {

    private val menuId: String = "id"
    private val menu = createMenu()
    private val products = listOf(
        createProduct(),
        createProduct(),
        createProduct()
    )
    private val uiItems = obtainUiItems(menu.categories, products)
    private val orderRepository = mockk<OrderRepository> {
        justRun { updateOrder(any(), any()) }
    }
    private val getMenuUseCase = mockk<GetMenuUseCase> {
        coEvery { this@mockk.invoke(menuId) } returns GetMenuResponse.Success(menu, products)
    }

    private fun viewModel() = MenuItemsViewModel(
        getMenuUseCase,
        orderRepository,
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
            assertEquals(uiItems, awaitItem().items)
        }
    }

    @Test
    fun given_loadMenu_when_getMenuSuccessful_then_showTabs() = testScope.runTest {
        viewModel().state.test {
            skipItems(1)
            val expected = (menu.categories?.map { it.name } ?: emptyList())
            assertEquals(expected, awaitItem().tabs)
        }
    }

    @Test
    fun given_loadMenu_when_getMenuFails_then_showError() = testScope.runTest {
        coEvery { getMenuUseCase.invoke(menuId) } returns GetMenuResponse.Error(GetMenuError.Unknown)
        viewModel().state.test {
            skipItems(1)
            assertNotNull(awaitItem().showError)
        }
    }

    @Test
    fun when_tabClicked_then_showTab_and_showCategory() = testScope.runTest {
        val index = 2
        val viewModel = viewModel()
        viewModel.state.test {
            skipItems(2)
            viewModel.eventHandler(Event.TabClicked(index))
            val state = awaitItem()
            assertEquals(index, state.currentTab)
            assertEquals(findCategoryId(uiItems, index.toString()), state.currentCategory)
        }
    }

    @Test
    fun when_decreaseQuantityClicked_then_changeQuantity_and_updateOrder() = testScope.runTest {
        val viewModel = viewModel()
        val index = 0
        runCurrent()
        val product = viewModel.state.value.items[index]

        viewModel.eventHandler(Event.DecreaseQuantityClicked(product.id))

        assertEquals(-1, (viewModel.state.value.items[index] as ProductUiItem).quantity)
        verify { orderRepository.updateOrder(product.id, -1) }
    }

    @Test
    fun when_increaseQuantityClicked_then_updateItems_and_updateOrder() = testScope.runTest {
        val viewModel = viewModel()
        val index = 0
        runCurrent()
        val product = viewModel.state.value.items[index]

        viewModel.eventHandler(Event.IncreaseQuantityClicked(product.id))

        assertEquals(1, (viewModel.state.value.items[index] as ProductUiItem).quantity)
        verify { orderRepository.updateOrder(product.id, 1) }
    }
}