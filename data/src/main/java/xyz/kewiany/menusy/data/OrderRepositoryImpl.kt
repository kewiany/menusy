package xyz.kewiany.menusy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.data.database.dao.CacheDao
import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.data.database.dao.ProductDao
import xyz.kewiany.menusy.data.database.entity.OrderEntity
import xyz.kewiany.menusy.data.database.entity.toCachedOrderedProduct
import xyz.kewiany.menusy.data.database.entity.toOrderedProduct
import xyz.kewiany.menusy.data.database.entity.toProductEntity
import xyz.kewiany.menusy.data.datastore.PlaceDataStore
import xyz.kewiany.menusy.domain.repository.OrderRepository
import xyz.kewiany.menusy.model.OrderedProduct
import xyz.kewiany.menusy.model.OrderedProductsData
import xyz.kewiany.menusy.model.Product
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val placeDataStore: PlaceDataStore,
    private val cacheDao: CacheDao,
    private val orderDao: OrderDao,
    private val productDao: ProductDao
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = cacheDao.getQuantity()
        .map(List<Int>::sum)

    override suspend fun getOrderedProductsData(): OrderedProductsData {
        val products = getOrderedProducts()
        val totalQuantity = calculateTotalQuantity(products)
        val totalPrice = calculateTotalPrice(products)
        return OrderedProductsData(
            products = products,
            totalQuantity = totalQuantity,
            totalPrice = totalPrice
        )
    }

    private suspend fun getOrderedProducts(): List<OrderedProduct> {
        return cacheDao.getAll()
            .map { cachedOrderedProduct -> cachedOrderedProduct.toOrderedProduct() }
    }

    private fun calculateTotalQuantity(products: List<OrderedProduct>): Int {
        return products.sumOf { it.quantity }
    }

    private fun calculateTotalPrice(products: List<OrderedProduct>): BigDecimal {
        val totalPrice = products.sumOf { it.product.price.multiply(it.quantity.toBigDecimal()) }
        return totalPrice.setScale(2, RoundingMode.FLOOR)
    }

    override suspend fun saveOrderToHistory(date: String) {
        val data = getOrderedProductsData()
        val place = placeDataStore.place.first()

        val orderEntity = OrderEntity(
            date = date,
            totalQuantity = data.totalQuantity,
            totalPrice = data.totalPrice.toFloat(),
            placeName = place.name,
            placeAddress = place.address
        )
        val orderId = orderDao.insert(orderEntity)

        val productEntities = data.products
            .map { orderedProduct -> orderedProduct.toProductEntity(orderId) }
        productDao.insertAll(productEntities)

        updateOrderedProducts(emptyList())
    }

    override suspend fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = getOrderedProducts().toMutableList()
        val inOrder = orderedProducts.firstOrNull { it.product.id == product.id }.run { this != null }

        if (inOrder) {
            val index = orderedProducts.indexOfFirst { it.product.id == product.id }
            orderedProducts.removeAt(index)
            if (quantity > 0) {
                orderedProducts.add(index, OrderedProduct(quantity, product))
            }
        } else {
            orderedProducts.add(OrderedProduct(quantity, product))
        }

        updateOrderedProducts(orderedProducts)
    }

    private suspend fun updateOrderedProducts(orderedProducts: List<OrderedProduct>) {
        val cachedOrderedProducts = orderedProducts.map { it.toCachedOrderedProduct() }
        cacheDao.deleteAll()
        cacheDao.insertAll(cachedOrderedProducts)
    }

    override suspend fun deleteOrder(productId: String) {
        val orderedProducts = getOrderedProducts().toMutableList()
        val index = orderedProducts.indexOfFirst { it.product.id == productId }
        orderedProducts.removeAt(index)
        updateOrderedProducts(orderedProducts)
    }
}