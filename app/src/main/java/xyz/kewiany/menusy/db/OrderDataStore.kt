package xyz.kewiany.menusy.db

import javax.inject.Inject

interface OrderDataStore {
    suspend fun insert(name: String, description: String, price: String, quantity: Int)
    suspend fun getAll(): List<OrderEntity>
}

class OrderDataStoreImpl @Inject constructor(private val database: AppDatabase) : OrderDataStore {

    override suspend fun insert(
        name: String,
        description: String,
        price: String,
        quantity: Int
    ) {
        val dao = database.orderDao()
        val entity = OrderEntity(
            productName = name,
            productDescription = description,
            productPrice = price,
            quantity = quantity
        )
        dao.insert(entity)
    }

    override suspend fun getAll(): List<OrderEntity> {
        val dao = database.orderDao()
        return dao.getAll()
    }
}