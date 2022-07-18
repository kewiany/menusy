package xyz.kewiany.menusy.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.data.source.local.InMemoryDataHolder
import xyz.kewiany.menusy.data.source.local.OrderDataSource
import xyz.kewiany.menusy.data.source.local.entity.OrderWithProducts
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject


class OrderRepositoryImpl @Inject constructor(
    private val inMemoryDataHolder: InMemoryDataHolder,
    private val orderDataStore: OrderDataSource
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = inMemoryDataHolder.orderedProductsCount

    override fun getOrderedProducts(): List<OrderedProduct> {
        return inMemoryDataHolder.orderedProducts.value
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
        inMemoryDataHolder.updateOrderedProducts(orderedProducts)
    }
}