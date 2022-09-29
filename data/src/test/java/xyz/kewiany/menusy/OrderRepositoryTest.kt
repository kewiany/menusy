package xyz.kewiany.menusy

import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.data.OrderRepositoryImpl
import xyz.kewiany.menusy.data.database.OrderLocalDataSource
import xyz.kewiany.menusy.data.database.dao.OrderedProductDao
import xyz.kewiany.menusy.data.database.dao.ProductDao
import xyz.kewiany.menusy.data.datastore.PlaceDataStore
import xyz.kewiany.menusy.domain.repository.OrderRepository
import xyz.kewiany.menusy.model.Place
import xyz.kewiany.menusy.test.common.BaseTest
import xyz.kewiany.menusy.test.common.createProduct
import java.time.LocalDateTime

class OrderRepositoryTest : BaseTest() {

    private val place = Place(
        id = "id",
        name = "name",
        address = "address"
    )
    private val placeDataStore = mockk<PlaceDataStore> {
        every { place } returns flowOf(this@OrderRepositoryTest.place)
    }
    private val products = listOf(
        createProduct(
            id = "id1",
            price = 10.10f.toBigDecimal()
        ),
        createProduct(
            id = "id2",
            price = 10.10f.toBigDecimal()
        )
    )
    private val orderedProducts = listOf(
        createCachedOrderedProductEntity(
            productId = products[0].id,
            quantity = 2,
            price = products[0].price.toFloat()
        ),
        createCachedOrderedProductEntity(
            productId = products[1].id,
            quantity = 2,
            price = products[1].price.toFloat()
        )
    )
    private val orderedProductDao = mockk<OrderedProductDao> {
        every { getQuantity() } returns flowOf(listOf(orderedProducts[0].quantity, orderedProducts[1].quantity))
        coEvery { getAll() } returns orderedProducts
        coJustRun { deleteAll() }
        coJustRun { insertAll(any()) }
    }
    private val orderId = 1010L
    private val orderLocalDataSource = mockk<OrderLocalDataSource> {
        coEvery { add(any(), any(), any(), any(), any()) } returns orderId
    }
    private val productDao = mockk<ProductDao> {
        coJustRun { insertAll(any()) }
    }

    private val orderRepository: OrderRepository = OrderRepositoryImpl(
        placeDataStore = placeDataStore,
        orderedProductDao = orderedProductDao,
        orderLocalDataSource = orderLocalDataSource,
        productDao = productDao
    )

    @Test
    fun getOrderedProducts() = testScope.runTest {
        val products = orderRepository.getOrderedProductsData().products

        coVerify { orderedProductDao.getAll() }
        assertEquals(orderedProducts.size, products.size)
    }

    @Test
    fun setTotalPriceOfOrderedProducts() = testScope.runTest {
        val products = orderRepository.getOrderedProductsData().products
        val totalPrice = products.sumOf { it.product.price.multiply(it.quantity.toBigDecimal()) }

        assertEquals(totalPrice, orderRepository.getOrderedProductsData().totalPrice)
    }

    @Test
    fun setTotalQuantityOfOrderedProducts() = testScope.runTest {
        val products = orderRepository.getOrderedProductsData().products
        val totalQuantity = products.sumOf { it.quantity }

        assertEquals(totalQuantity, orderRepository.getOrderedProductsData().totalQuantity)
    }

    @Test
    fun saveOrderToHistory() = testScope.runTest {
        val date = LocalDateTime.parse("2019-03-20T15:58:00")
        orderRepository.saveOrderToHistory(date)

        coVerify { productDao.insertAll(any()) }
        coVerify {
            orderLocalDataSource.add(
                date = date.toString(),
                totalQuantity = any(),
                totalPrice = any(),
                placeName = place.name,
                placeAddress = place.address
            )
        }
        coVerify { orderedProductDao.deleteAll() }
        coVerify { orderedProductDao.insertAll(emptyList()) }
    }

    @Test
    fun updateOrderWithProductInOrder() = testScope.runTest {
        val product = createProduct(
            id = "id1"
        )
        orderRepository.updateOrder(2, product)

        coVerify { orderedProductDao.getAll() }
        coVerify { orderedProductDao.deleteAll() }
        coVerify { orderedProductDao.insertAll(any()) }
    }

    @Test
    fun updateOrderWithoutProductInOrder() = testScope.runTest {
        val product = createProduct(
            id = "id3"
        )
        orderRepository.updateOrder(2, product)

        coVerify { orderedProductDao.getAll() }
        coVerify { orderedProductDao.deleteAll() }
        coVerify { orderedProductDao.insertAll(any()) }
    }

    @Test
    fun deleteOrder() = testScope.runTest {
        val product = orderedProducts[1]
        orderRepository.deleteOrder(product.productId)
        val newProducts = orderedProducts.toMutableList()
        newProducts.removeAt(1)

        coVerify { orderedProductDao.getAll() }
        coVerify { orderedProductDao.deleteAll() }
        coVerify { orderedProductDao.insertAll(newProducts) }
    }
}