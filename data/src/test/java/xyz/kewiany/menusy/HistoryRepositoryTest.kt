package xyz.kewiany.menusy

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.data.HistoryRepositoryImpl
import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.domain.repository.HistoryRepository
import xyz.kewiany.menusy.test.common.BaseTest

class HistoryRepositoryTest : BaseTest() {

    private val orderWithProductsEntities = listOf(createOrderWithProductsEntity())
    private val orderDao = mockk<OrderDao> {
        coEvery { getAll() } returns orderWithProductsEntities
    }
    private val historyRepository: HistoryRepository = HistoryRepositoryImpl(orderDao)

    @Test
    fun getHistoryOrders() = testScope.runTest {
        val historyOrders = historyRepository.getHistoryOrders()

        coVerify { orderDao.getAll() }
        assertEquals(orderWithProductsEntities.size, historyOrders.size)
    }
}