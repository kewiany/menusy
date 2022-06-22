package xyz.kewiany.menusy.db

import xyz.kewiany.menusy.OrderedProduct
import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

interface OrderDataStore {
    suspend fun insert(order: OrderedProduct)
    suspend fun getAll(): List<OrderedProduct>
}

class OrderDataStoreImpl @Inject constructor(private val database: AppDatabase) : OrderDataStore {

    override suspend fun insert(order: OrderedProduct) {
        val dao = database.orderDao()
        val entity = OrderEntity(
            productId = order.product.id,
            quantity = order.quantity
        )
        dao.insert(entity)
    }

    override suspend fun getAll(): List<OrderedProduct> {
        val dao = database.orderDao()
        return dao.getAll().map { entity ->
            OrderedProduct(
                quantity = entity.quantity,
                product = Product(entity.productId, "", "", "", "", "")
            )
        }
    }
}