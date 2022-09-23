package xyz.kewiany.menusy.data

import xyz.kewiany.menusy.data.database.dao.OrderDao
import xyz.kewiany.menusy.data.database.entity.OrderWithProductsEntity
import xyz.kewiany.menusy.data.database.entity.toHistoryOrder
import xyz.kewiany.menusy.domain.model.HistoryOrder
import xyz.kewiany.menusy.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : HistoryRepository {

    override suspend fun getHistoryOrders(): List<HistoryOrder> {
        return orderDao.getAll()
            .map(OrderWithProductsEntity::toHistoryOrder)
    }
}