package xyz.kewiany.menusy.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.common.OrderedProductsData
import xyz.kewiany.menusy.data.source.local.InMemoryDataHolder
import xyz.kewiany.menusy.data.source.local.OrderDataSource
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.OrderRepository
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val inMemoryDataHolder: InMemoryDataHolder,
    private val orderDataStore: OrderDataSource
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = inMemoryDataHolder.orderedProductsCount

    override fun getOrderedProducts(): OrderedProductsData {
        val products = inMemoryDataHolder.orderedProducts.value
        val totalQuantity = calculateTotalQuantity(products)
        val totalPrice = calculateTotalPrice(products)
        return OrderedProductsData(
            products = products,
            totalQuantity = totalQuantity,
            totalPrice = totalPrice
        )
    }

    private fun calculateTotalQuantity(products: List<OrderedProduct>): Int {
        return products.sumOf { it.quantity }
    }

    private fun calculateTotalPrice(products: List<OrderedProduct>): BigDecimal {
        val totalPrice = products.sumOf { it.product.price.multiply(it.quantity.toBigDecimal()) }
        return totalPrice.setScale(2, RoundingMode.FLOOR)
    }

    override suspend fun saveOrderToHistory(
        products: List<OrderedProduct>,
        date: String,
        totalQuantity: Int,
        totalPrice: BigDecimal,
        placeName: String,
        placeAddress: String
    ) {
        orderDataStore.insert(
            orderedProducts = products,
            date = date,
            totalQuantity = totalQuantity,
            totalPrice = totalPrice,
            placeName = placeName,
            placeAddress = placeAddress
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