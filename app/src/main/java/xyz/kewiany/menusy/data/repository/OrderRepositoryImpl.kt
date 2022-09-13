package xyz.kewiany.menusy.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.common.OrderedProductsData
import xyz.kewiany.menusy.data.source.local.InMemoryDataHolder
import xyz.kewiany.menusy.data.source.local.OrderDataSource
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject


class OrderRepositoryImpl @Inject constructor(
    private val inMemoryDataHolder: InMemoryDataHolder,
    private val orderDataStore: OrderDataSource
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = inMemoryDataHolder.orderedProductsCount

    override fun getOrderedProducts(): OrderedProductsData {
        val products = inMemoryDataHolder.orderedProducts.value
        val totalPrice = calculateTotalPrice(products)
        val totalQuantity = calculateTotalQuantity(products)
        return OrderedProductsData(
            products = products,
            totalQuantity = totalQuantity,
            totalPrice = totalPrice
        )
    }

    private fun calculateTotalPrice(products: List<OrderedProduct>): Float {
        return products.sumOf { (it.quantity * it.product.price).toDouble() }.toFloat()
    }

    private fun calculateTotalQuantity(products: List<OrderedProduct>): Int {
        return products.sumOf { it.quantity }
    }

    override suspend fun saveOrderToHistory(
        products: List<OrderedProduct>,
        date: String,
        totalQuantity: Int,
        totalPrice: Float
    ) {
        orderDataStore.insert(
            orderedProducts = products,
            date = date,
            totalPrice = totalPrice,
            totalQuantity = totalQuantity
        )
        inMemoryDataHolder.updateOrderedProducts(emptyList())
    }

    override suspend fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = inMemoryDataHolder.orderedProducts.value.toMutableList()
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

        inMemoryDataHolder.updateOrderedProducts(orderedProducts)
    }

    override suspend fun deleteOrder(productId: String) {
        val orderedProducts = inMemoryDataHolder.orderedProducts.value.toMutableList()
        val index = orderedProducts.indexOfFirst { it.product.id == productId }
        orderedProducts.removeAt(index)
        inMemoryDataHolder.updateOrderedProducts(orderedProducts)
    }
}