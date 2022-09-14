package xyz.kewiany.menusy.data.source.local

import xyz.kewiany.menusy.data.source.local.dao.OrderDao
import xyz.kewiany.menusy.data.source.local.dao.ProductDao
import xyz.kewiany.menusy.data.source.local.entity.OrderEntity
import xyz.kewiany.menusy.data.source.local.entity.OrderWithProductsEntity
import xyz.kewiany.menusy.data.source.local.entity.ProductEntity
import xyz.kewiany.menusy.domain.model.OrderedProduct
import java.math.BigDecimal
import javax.inject.Inject

interface OrderDataStore {

    suspend fun insert(
        orderedProducts: List<OrderedProduct>,
        date: String,
        totalQuantity: Int,
        totalPrice: BigDecimal,
        placeName: String,
        placeAddress: String
    )

    suspend fun getAll(): List<OrderWithProductsEntity>
}

class OrderDataStoreImpl @Inject constructor(
    private val productDao: ProductDao,
    private val orderDao: OrderDao
) : OrderDataStore {

    override suspend fun insert(
        orderedProducts: List<OrderedProduct>,
        date: String,
        totalQuantity: Int,
        totalPrice: BigDecimal,
        placeName: String,
        placeAddress: String
    ) {
        val orderEntity = OrderEntity(
            date = date,
            totalQuantity = totalQuantity,
            totalPrice = totalPrice.toFloat(),
            placeName = placeName,
            placeAddress = placeAddress
        )
        val orderId = orderDao.insert(orderEntity)
        val productEntities = orderedProducts.map { orderedProduct ->
            with(orderedProduct) {
                ProductEntity(
                    orderId = orderId,
                    name = product.name,
                    description = product.description,
                    price = product.price.toString(),
                    quantity = quantity,
                )
            }
        }
        productDao.insertAll(productEntities)
    }

    override suspend fun getAll(): List<OrderWithProductsEntity> {
        return orderDao.getAll()
    }
}