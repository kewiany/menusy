package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.kewiany.menusy.db.OrderDataStore
import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

interface OrderRepository {
    val order: StateFlow<List<OrderedProduct>>
    suspend fun getOrdersFromHistory(): List<OrderedProduct>
    suspend fun saveOrderToHistory(orderedProducts: List<OrderedProduct>)
    suspend fun updateOrder(quantity: Int, product: Product)
}

class OrderRepositoryImpl @Inject constructor(
    private val orderDataStore: OrderDataStore
) : OrderRepository {

    private val _order = MutableStateFlow<List<OrderedProduct>>(emptyList())
    override val order = _order.asStateFlow()

    override suspend fun getOrdersFromHistory(): List<OrderedProduct> {
        return orderDataStore.getAll()
    }

    override suspend fun saveOrderToHistory(orderedProducts: List<OrderedProduct>) {
        orderedProducts.forEach { orderedProduct -> orderDataStore.insert(orderedProduct) }
    }

    override suspend fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = order.value.toMutableList()
        val inOrder = orderedProducts.firstOrNull { it.product.id == product.id }
            ?.run { true }
            ?: false

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
}