package xyz.kewiany.menusy.domain.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.model.OrderedProductsData
import xyz.kewiany.menusy.model.Product
import java.time.LocalDateTime

interface OrderRepository {
    val orderedProductsCount: Flow<Int>
    suspend fun getOrderedProductsData(): OrderedProductsData
    suspend fun updateOrder(quantity: Int, product: Product)
    suspend fun saveOrderToHistory(date: LocalDateTime)
    suspend fun deleteOrder(productId: String)
}