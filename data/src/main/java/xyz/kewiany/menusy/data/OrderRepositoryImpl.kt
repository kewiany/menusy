package xyz.kewiany.menusy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.data.database.dao.OrderedProductDao
import xyz.kewiany.menusy.data.database.dao.ProductDao
import xyz.kewiany.menusy.data.database.entity.*
import xyz.kewiany.menusy.data.datastore.PlaceDataStore
import xyz.kewiany.menusy.domain.repository.OrderRepository
import xyz.kewiany.menusy.model.OrderedProduct
import xyz.kewiany.menusy.model.OrderedProductsData
import xyz.kewiany.menusy.model.Product
import java.math.BigDecimal
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val placeDataStore: PlaceDataStore,
    private val orderedProductDao: OrderedProductDao,
    private val orderDao: OrderDao,
    private val productDao: ProductDao
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = orderedProductDao.getQuantity().map(List<Int>::sum)

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

    override suspend fun saveOrderToHistory(date: String) {
        val orderedProductsData = getOrderedProductsData()
        val place = placeDataStore.place.first()

        val orderEntity = OrderEntity(
            date = date,
            totalQuantity = orderedProductsData.totalQuantity,
            totalPrice = orderedProductsData.totalPrice.toFloat(),
            placeName = place.name,
            placeAddress = place.address
        )
        val orderId = addOrder(orderEntity)

        val productEntities = orderedProductsData.products.map { it.toProductEntity(orderId) }
        addProducts(productEntities)

        updateOrderedProducts(emptyList())
    }

    private suspend fun addOrder(orderEntity: OrderEntity): Long {
        return orderDao.insert(orderEntity)
    }

    private suspend fun addProducts(productEntities: List<ProductEntity>) {
        productDao.insertAll(productEntities)
    }

    override suspend fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = getOrderedProducts().toMutableList()
        val isProductInOrder = orderedProducts.firstOrNull { it.product.id == product.id }.run { this != null }

        if (isProductInOrder) {
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

    override suspend fun deleteOrder(productId: String) {
        val orderedProducts = getOrderedProducts().toMutableList()
        val index = orderedProducts.indexOfFirst { it.product.id == productId }
        orderedProducts.removeAt(index)

        updateOrderedProducts(orderedProducts)
    }

    private suspend fun getOrderedProducts(): List<OrderedProduct> {
        return orderedProductDao.getAll().map { cachedOrderedProduct -> cachedOrderedProduct.toOrderedProduct() }
    }

    private suspend fun updateOrderedProducts(orderedProducts: List<OrderedProduct>) {
        val cachedOrderedProducts = orderedProducts.map { it.toCachedOrderedProduct() }
        orderedProductDao.deleteAll()
        orderedProductDao.insertAll(cachedOrderedProducts)
    }

    private fun calculateTotalQuantity(products: List<OrderedProduct>): Int {
        return products.sumOf { it.quantity }
    }

    private fun calculateTotalPrice(products: List<OrderedProduct>): BigDecimal {
        return products.sumOf { it.product.price.multiply(it.quantity.toBigDecimal()) }
    }
}