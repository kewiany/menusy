package xyz.kewiany.menusy.viewmodel

import createHistoryOrder
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.domain.usecase.order.GetOrdersFromHistoryUseCase
import xyz.kewiany.menusy.presentation.features.history.HistoryViewModel
import xyz.kewiany.menusy.presentation.features.history.HistoryViewModel.Event

class HistoryViewModelTest : BaseTest() {

    private val historyOrders = listOf(
        createHistoryOrder(),
        createHistoryOrder()
    )
    private val getOrdersFromHistoryUseCase = mockk<GetOrdersFromHistoryUseCase> {
        coEvery { this@mockk.invoke() } returns historyOrders
    }

    private fun viewModel() = HistoryViewModel(
        getOrdersFromHistoryUseCase
    )

    @Test
    fun when_triggerLoadHistory_then_setItems() = testScope.runTest {
        val expectedCount = historyOrders.count() + historyOrders.map { it.products.count() }.sum()
        val viewModel = viewModel()

        viewModel.eventHandler(Event.TriggerLoadHistory)
        runCurrent()
        val state = viewModel.state.value

        val actualCount = state.items.count()
        assertEquals(expectedCount, actualCount)
    }
}