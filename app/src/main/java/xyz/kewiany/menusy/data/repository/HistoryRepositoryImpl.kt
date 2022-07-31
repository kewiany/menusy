package xyz.kewiany.menusy.data.repository

import xyz.kewiany.menusy.data.source.local.OrderDataSource
import xyz.kewiany.menusy.data.source.local.entity.OrderWithProductsEntity
import xyz.kewiany.menusy.data.source.local.entity.toHistoryOrder
import xyz.kewiany.menusy.domain.model.HistoryOrder
import xyz.kewiany.menusy.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : HistoryRepository {

    override suspend fun getHistoryOrders(): List<HistoryOrder> {
        return orderDataSource.getAll()
            .map(OrderWithProductsEntity::toHistoryOrder)
    }
}