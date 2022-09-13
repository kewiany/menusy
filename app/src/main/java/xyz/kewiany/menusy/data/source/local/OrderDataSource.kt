package xyz.kewiany.menusy.data.source.local

import xyz.kewiany.menusy.data.source.local.dao.OrderDao
import xyz.kewiany.menusy.data.source.local.dao.ProductDao
import xyz.kewiany.menusy.data.source.local.entity.OrderEntity
import xyz.kewiany.menusy.data.source.local.entity.OrderWithProductsEntity
import xyz.kewiany.menusy.data.source.local.entity.ProductEntity
import xyz.kewiany.menusy.domain.model.OrderedProduct
import java.math.BigDecimal
import javax.inject.Inject

interface OrderDataSource {
    suspend fun insert(
        orderedProducts: List<OrderedProduct>,
        date: String,
        totalPrice: BigDecimal,
        totalQuantity: Int
    )

    suspend fun getAll(): List<OrderWithProductsEntity>
}

class OrderDataSourceImpl @Inject constructor(
    private val productDao: ProductDao,
    private val orderDao: OrderDao
) : OrderDataSource {

    override suspend fun insert(
        orderedProducts: List<OrderedProduct>,
        date: String,
        totalPrice: BigDecimal,
        totalQuantity: Int
    ) {
        val orderEntity = OrderEntity(
            date = date,
            totalPrice = totalPrice.toFloat(),
            totalQuantity = totalQuantity
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