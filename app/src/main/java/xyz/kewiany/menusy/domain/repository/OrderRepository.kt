package xyz.kewiany.menusy.domain.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.common.OrderedProductsData
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import java.math.BigDecimal

interface OrderRepository {
    val orderedProductsCount: Flow<Int>
    fun getOrderedProducts(): OrderedProductsData
    suspend fun updateOrder(quantity: Int, product: Product)
    suspend fun saveOrderToHistory(
        products: List<OrderedProduct>,
        date: String,
        totalQuantity: Int,
        totalPrice: BigDecimal
    )

    suspend fun deleteOrder(productId: String)
}