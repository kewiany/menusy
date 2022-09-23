package xyz.kewiany.menusy.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import xyz.kewiany.menusy.data.source.local.OrderDataStore
import xyz.kewiany.menusy.data.source.local.OrderedProductsDataStore
import xyz.kewiany.menusy.data.source.local.PlaceDataStore
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.OrderedProductsData
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.OrderRepository
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderedProductsDataStore: OrderedProductsDataStore,
    private val placeDataStore: PlaceDataStore,
    private val orderDataStore: OrderDataStore
) : OrderRepository {

    override val orderedProductsCount: Flow<Int> = orderedProductsDataStore.orderedProductsCount

    override suspend fun getOrderedProducts(): OrderedProductsData {
        val products = orderedProductsDataStore.getOrderedProducts()
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

    override suspend fun saveOrderToHistory(date: String) {
        val orderedProductsData = getOrderedProducts()
        val cachedPlace = placeDataStore.place.first()
        orderDataStore.insert(
            orderedProducts = orderedProductsData.products,
            date = date,
            totalQuantity = orderedProductsData.totalQuantity,
            totalPrice = orderedProductsData.totalPrice,
            placeName = cachedPlace.name,
            placeAddress = cachedPlace.address
        )
        orderedProductsDataStore.updateOrderedProducts(emptyList())
    }

    override suspend fun updateOrder(quantity: Int, product: Product) {
        val orderedProducts = orderedProductsDataStore.getOrderedProducts().toMutableList()
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

        orderedProductsDataStore.updateOrderedProducts(orderedProducts)
    }

    override suspend fun deleteOrder(productId: String) {
        val orderedProducts = orderedProductsDataStore.getOrderedProducts().toMutableList()
        val index = orderedProducts.indexOfFirst { it.product.id == productId }
        orderedProducts.removeAt(index)
        orderedProductsDataStore.updateOrderedProducts(orderedProducts)
    }
}