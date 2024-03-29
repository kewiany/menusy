package xyz.kewiany.menusy.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.domain.usecase.order.GetOrdersFromHistoryUseCase
import xyz.kewiany.menusy.feature.history.HistoryOrderMapper
import xyz.kewiany.menusy.feature.history.HistoryViewModel
import xyz.kewiany.menusy.feature.history.HistoryViewModel.Event
import xyz.kewiany.menusy.test.common.BaseTest
import xyz.kewiany.menusy.test.common.createHistoryOrder

class HistoryViewModelTest : BaseTest() {

    private val historyOrders = listOf(
        createHistoryOrder(),
        createHistoryOrder()
    )
    private val getOrdersFromHistoryUseCase = mockk<GetOrdersFromHistoryUseCase> {
        coEvery { this@mockk.invoke() } returns historyOrders
    }
    private val historyOrderMapper = mockk<HistoryOrderMapper>(relaxed = true)

    private fun viewModel() = HistoryViewModel(
        getOrdersFromHistoryUseCase,
        historyOrderMapper
    )

    @Test
    fun when_triggerLoadHistory_then_setItems() = testScope.runTest {
        val expectedCount = historyOrders.count() + historyOrders.sumOf { it.products.count() }
        val viewModel = viewModel()

        viewModel.eventHandler(Event.TriggerLoadHistory)
        runCurrent()
        val state = viewModel.state.value

        val actualCount = state.items.count()
        assertEquals(expectedCount, actualCount)
    }
}