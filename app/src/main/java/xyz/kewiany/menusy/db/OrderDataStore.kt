package xyz.kewiany.menusy.db

import xyz.kewiany.menusy.OrderedProduct
import javax.inject.Inject

interface OrderDataStore {
    suspend fun insert(
        date: String,
        orderedProducts: List<OrderedProduct>,
        totalPrice: Float,
        totalQuantity: Int
    )

    suspend fun getAll(): List<OrderWithProducts>
}

class OrderDataStoreImpl @Inject constructor(private val database: AppDatabase) : OrderDataStore {

    override suspend fun insert(
        date: String,
        orderedProducts: List<OrderedProduct>,
        totalPrice: Float,
        totalQuantity: Int
    ) {
        val orderDao = database.orderDao()
        val orderEntity = OrderEntity(
            date = date,
            totalPrice = totalPrice,
            totalQuantity = totalQuantity
        )
        val orderId = orderDao.insert(orderEntity)
        val productDao = database.productDao()
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

    override suspend fun getAll(): List<OrderWithProducts> {
        val dao = database.orderDao()
        return dao.getAll()
    }
}