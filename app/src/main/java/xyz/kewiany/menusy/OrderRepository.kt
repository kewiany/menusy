package xyz.kewiany.menusy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.db.OrderDataStore
import xyz.kewiany.menusy.db.OrderWithProducts
import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

interface OrderRepository {
    val order: StateFlow<List<OrderedProduct>>
    val productsOrderedCount: Flow<Int>
    suspend fun getOrdersFromHistory(): List<OrderWithProducts>
    suspend fun saveOrderToHistory(orderedProducts: List<OrderedProduct>)
    suspend fun updateOrder(quantity: Int, product: Product)
    suspend fun clear()
}

class OrderRepositoryImpl @Inject constructor(
    private val orderDataStore: OrderDataStore
) : OrderRepository {

    private val _order: MutableStateFlow<List<OrderedProduct>> = MutableStateFlow(emptyList())
    override val order: StateFlow<List<OrderedProduct>> = _order
    override val productsOrderedCount: Flow<Int> = _order
        .map { orderedProducts ->
            var quantity = 0
            orderedProducts.forEach { quantity += it.quantity }
            quantity
        }

    override suspend fun getOrdersFromHistory(): List<OrderWithProducts> {
        return orderDataStore.getAll()
    }

    override suspend fun saveOrderToHistory(orderedProducts: List<OrderedProduct>) {
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
        products.map { it.product.price }
            .forEach { price -> totalPrice += price }
        return totalPrice
    }

    private fun calculateTotalQuantity(products: List<OrderedProduct>): Int {
        var totalQuantity = 0
        products.map { it.quantity }
            .forEach { quantity -> totalQuantity += quantity }
        return totalQuantity
    }

    override suspend fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = order.value.toMutableList()
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
        _order.value = orderedProducts
    }

    override suspend fun clear() {
        _order.value = emptyList()
    }
}