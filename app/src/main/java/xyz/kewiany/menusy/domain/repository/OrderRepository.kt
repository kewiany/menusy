package xyz.kewiany.menusy.domain.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product

interface OrderRepository {
    val orderedProductsCount: Flow<Int>
    fun getOrderedProducts(): List<OrderedProduct>
    suspend fun updateOrder(quantity: Int, product: Product)
    suspend fun saveOrderToHistory(orderedProducts: List<OrderedProduct>)
    suspend fun deleteOrder(productId: String)
}