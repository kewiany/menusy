//package xyz.kewiany.menusy.viewmodel
//
//import io.mockk.coEvery
//import io.mockk.coJustRun
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.test.runCurrent
//import kotlinx.coroutines.test.runTest
//import org.junit.Assert.*
//import org.junit.Test
//import xyz.kewiany.menusy.common.Result
//import xyz.kewiany.menusy.domain.usecase.order.UpdateOrderUseCase
//import xyz.kewiany.menusy.feature.menu.GetMenuContentFacade
//import xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel.Event
//import xyz.kewiany.menusy.test.common.BaseTest
//import xyz.kewiany.menusy.test.common.createCategory
//import xyz.kewiany.menusy.test.common.createCategoryTab
//import xyz.kewiany.menusy.test.common.createProduct
//
//class MenuItemsViewModelTest : BaseTest() {
//
//    private val menuId: String = "id"
//    private val tab = createCategoryTab("id1")
//    private val category = createCategoryUIItem(createCategory("id1"))
//    private val product = createProductUIItem(
//        product = createProduct(categoryId = "id1"),
//        quantity = 2
//    )
//    private val content = createContent(
//        tabs = listOf(
//            tab,
//            createCategoryTab("id2")
//        ),
//        items = listOf(
//            category,
//            product,
//            createCategoryUIItem(createCategory("id2")),
//            createProductUIItem(createProduct(categoryId = "id2"))
//        )
//    )
//    private val getMenuContentFacade = mockk<GetMenuContentFacade> {
//        coEvery { getContent(menuId) } returns Result.Success(content)
//    }
//    private val updateOrderUseCase = mockk<UpdateOrderUseCase> {
//        coJustRun { this@mockk.invoke(any(), any()) }
//    }
//
//    private fun viewModel() = xyz.kewiany.menusy.feature.menu.items.MenuItemsViewModel(
//        getMenuContentFacade,
//        updateOrderUseCase,
//        menuId,
//        testDispatcherProvider
//    )
//
//    @Test
//    fun when_loadMenuTriggered_then_setLoading_and_setItems() = testScope.runTest {
//        val expectedCount = content.tabs.count() + content.items.count()
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        runCurrent()
//
//        val state = viewModel.state.value
//        val actualCount = state.tabs.count() + state.items.count()
//        coVerify { getMenuContentFacade.getContent(any()) }
//        assertEquals(expectedCount, actualCount)
//        assertFalse(state.showLoading)
//    }
//
//    @Test
//    fun when_triggerLoadMenusError_then_setLoading_and_showError() = testScope.runTest {
//        coEvery { getMenuContentFacade.getContent(any()) } returns Result.Error()
//        val expectedCount = 0
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        runCurrent()
//
//        val state = viewModel.state.value
//        val actualCount = state.items.count()
//        coVerify { getMenuContentFacade.getContent(any()) }
//        assertEquals(expectedCount, actualCount)
//        assertFalse(state.showLoading)
//        assertNotNull(state.showError)
//    }
//
//    @Test
//    fun given_showError_when_errorOkClicked_then_hideError() = testScope.runTest {
//        coEvery { getMenuContentFacade.getContent(menuId) } returns Result.Error()
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        viewModel.eventHandler(Event.ErrorOKClicked)
//
//        val state = viewModel.state.value
//        assertFalse(state.showError)
//    }
//
//    @Test
//    fun given_showError_when_errorDismissedTriggered_then_hideError() = testScope.runTest {
//        coEvery { getMenuContentFacade.getContent(menuId) } returns Result.Error()
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        viewModel.eventHandler(Event.TriggerDismissError)
//
//        val state = viewModel.state.value
//        assertFalse(state.showError)
//    }
//
//    @Test
//    fun when_tabClicked_then_showTab_and_showCategory() = testScope.runTest {
//        val expectedTabIndex = 1
//        val expectedCategoryIndex = 2
//        val tab = content.tabs[expectedTabIndex]
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        runCurrent()
//        viewModel.eventHandler(Event.TabClicked(tab.id))
//
//        val state = viewModel.state.value
//        assertEquals(expectedTabIndex, state.currentTab)
//        assertEquals(expectedCategoryIndex, state.currentCategory)
//    }
//
//    @Test
//    fun when_decreaseQuantityClicked_then_updateOrder_and_setItems() = testScope.runTest {
//        val id = product.id
//        val quantity = product.quantity
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        runCurrent()
//        viewModel.eventHandler(Event.DecreaseQuantityClicked(product.id))
//        runCurrent()
//
//        coVerify { updateOrderUseCase(quantity - 1, id) }
//    }
//
//    @Test
//    fun when_increaseQuantityClicked_then_updateOrder_and_setItems() = testScope.runTest {
//        val id = product.id
//        val quantity = product.quantity
//        val viewModel = viewModel()
//
//        viewModel.eventHandler(Event.TriggerLoadMenu)
//        runCurrent()
//        viewModel.eventHandler(Event.IncreaseQuantityClicked(id))
//        runCurrent()
//
//        coVerify { updateOrderUseCase(quantity + 1, id) }
//    }
//}