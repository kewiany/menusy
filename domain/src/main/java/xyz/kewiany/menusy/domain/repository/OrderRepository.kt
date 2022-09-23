package xyz.kewiany.menusy.domain.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.domain.model.OrderedProductsData
import xyz.kewiany.menusy.domain.model.Product

interface OrderRepository {
    val orderedProductsCount: Flow<Int>
    suspend fun getOrderedProducts(): OrderedProductsData
    suspend fun updateOrder(quantity: Int, product: Product)
    suspend fun saveOrderToHistory(date: String)
    suspend fun deleteOrder(productId: String)
}