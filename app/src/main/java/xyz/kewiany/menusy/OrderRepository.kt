package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xyz.kewiany.menusy.db.OrderDataStore
import xyz.kewiany.menusy.db.OrderWithProducts
import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

interface OrderRepository {
    val order: StateFlow<List<OrderedProduct>>
    val orderedProductsCount: StateFlow<Int>
    suspend fun getOrdersFromHistory(): List<OrderWithProducts>
    suspend fun finishOrder()
    fun updateOrder(quantity: Int, product: Product)
}

class OrderRepositoryImpl @Inject constructor(
    private val orderDataStore: OrderDataStore
) : OrderRepository {

    private val _order = MutableStateFlow<List<OrderedProduct>>(emptyList())
    override val order: StateFlow<List<OrderedProduct>> get() = _order

    private val _orderedProductsCount = MutableStateFlow(0)
    override val orderedProductsCount: StateFlow<Int> = _orderedProductsCount

    override suspend fun getOrdersFromHistory(): List<OrderWithProducts> {
        return orderDataStore.getAll()
    }

    override suspend fun finishOrder() {
        val orderedProducts = _order.value
        saveOrderToHistory(orderedProducts)
        updateOrderedProducts(emptyList())
        updateOrderedProductsCount(emptyList())
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

    override fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = _order.value.toMutableList()
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
        updateOrderedProductsCount(orderedProducts)
    }

    private fun updateOrderedProducts(orderedProducts: List<OrderedProduct>) {
        _order.value = orderedProducts
    }

    private fun updateOrderedProductsCount(orderedProducts: List<OrderedProduct>) {
        var quantity = 0
        orderedProducts.forEach { quantity += it.quantity }
        _orderedProductsCount.value = quantity
    }
}