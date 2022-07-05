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

    suspend fun getAll(): List<OrderEntity>
}

class OrderDataStoreImpl @Inject constructor(private val database: AppDatabase) : OrderDataStore {

    override suspend fun insert(
        date: String,
        orderedProducts: List<OrderedProduct>,
        totalPrice: Float,
        totalQuantity: Int
    ) {
        val dao = database.orderDao()
        val entity = OrderEntity(
            date = date,
            products = orderedProducts.map { orderedProduct ->
                with(orderedProduct) {
                    ProductEntity(
                        name = product.name,
                        description = product.description,
                        price = product.price.toString(),
                        quantity = quantity,
                    )
                }
            },
            totalPrice = totalPrice,
            totalQuantity = totalQuantity
        )
        dao.insert(entity)
    }

    override suspend fun getAll(): List<OrderEntity> {
        val dao = database.orderDao()
        return dao.getAll()
    }
}