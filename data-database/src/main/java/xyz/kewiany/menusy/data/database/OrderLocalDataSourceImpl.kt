package xyz.kewiany.menusy.data.database

import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.data.database.entity.OrderEntity
import javax.inject.Inject

class OrderLocalDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderLocalDataSource {

    override suspend fun add(
        date: String,
        totalQuantity: Int,
        totalPrice: Float,
        placeName: String,
        placeAddress: String
    ): Long {
        val orderEntity = OrderEntity(
            date = date,
            totalQuantity = totalQuantity,
            totalPrice = totalPrice,
            placeName = placeName,
            placeAddress = placeAddress
        )
        return orderDao.insert(orderEntity)
    }
}