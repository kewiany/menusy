package xyz.kewiany.menusy

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.db.OrderDataStore
import xyz.kewiany.menusy.db.OrderWithProducts
import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

interface OrderRepository {
    val orderedProductsCount: Flow<Int>
    fun getOrderedProducts(): List<OrderedProduct>
    suspend fun getOrdersFromHistory(): List<OrderWithProducts>
    suspend fun finishOrder()
    suspend fun updateOrder(quantity: Int, product: Product)
}

class OrderRepositoryImpl @Inject constructor(
    private val inMemoryDataStore: InMemoryDataStore,
    private val orderDataStore: OrderDataStore
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = inMemoryDataStore.orderedProductsCount

    override fun getOrderedProducts(): List<OrderedProduct> {
        return inMemoryDataStore.orderedProducts.value
    }

    override suspend fun getOrdersFromHistory(): List<OrderWithProducts> {
        return orderDataStore.getAll()
    }

    override suspend fun finishOrder() {
        val orderedProducts = getOrderedProducts()
        saveOrderToHistory(orderedProducts)
        updateOrderedProducts(emptyList())
    }

    private suspend fun saveOrderToHistory(orderedProducts: List<OrderedProduct>) {
        val totalPrice = calculateTotalPrice(orderedProducts)
        val totalQuantity = calculateTotalQuantity(orderedProducts)
        orderDataStore.insert(
            date = "",
            orderedProducts = orderedProducts,
            totalPrice = totalPrice,
            totalQuantity = totalQuantity
        )
    }

    private fun calculateTotalPrice(products: List<OrderedProduct>): Float {
        var totalPrice = 0f
        products.map { it.product.price }.forEach { price -> totalPrice += price }
        return totalPrice
    }

    private fun calculateTotalQuantity(products: List<OrderedProduct>): Int {
        var totalQuantity = 0
        products.map { it.quantity }.forEach { quantity -> totalQuantity += quantity }
        return totalQuantity
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
        inMemoryDataStore.updateOrderedProducts(orderedProducts)
    }
}