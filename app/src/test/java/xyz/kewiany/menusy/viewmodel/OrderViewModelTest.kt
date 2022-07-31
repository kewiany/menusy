package xyz.kewiany.menusy.viewmodel

import createOrderedProduct
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.domain.usecase.order.FinishOrderUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel
import xyz.kewiany.menusy.presentation.features.order.OrderViewModel.Event

class OrderViewModelTest : BaseTest() {

    private val orderedProducts = listOf(
        createOrderedProduct(),
        createOrderedProduct()
    )
    private val getOrderedProductsUseCase = mockk<GetOrderedProductsUseCase> {
        coEvery { this@mockk.invoke() } returns orderedProducts
    }
    private val finishOrderUseCase = mockk<FinishOrderUseCase> {
        coJustRun { this@mockk.invoke() }
    }

    private fun viewModel() = OrderViewModel(
        getOrderedProductsUseCase,
        finishOrderUseCase
    )

    @Test
    fun when_triggerLoadOrder_then_setItems() = testScope.runTest {
        val expectedCount = orderedProducts.count()
        val viewModel = viewModel()

        viewModel.eventHandler(Event.TriggerLoadOrder)
        runCurrent()
        val state = viewModel.state.value

        val actualCount = state.results.count()
        assertEquals(expectedCount, actualCount)
    }

    @Test
    fun when_payClicked_then_finishOrder_and_setItems() = testScope.runTest {
        val expectedCount = 0
        val viewModel = viewModel()

        viewModel.eventHandler(Event.PayButtonClicked)
        runCurrent()
        val state = viewModel.state.value

        val actualCount = state.results.count()
        assertEquals(expectedCount, actualCount)
        coVerify { finishOrderUseCase.invoke() }
    }
}