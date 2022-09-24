package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.model.HistoryOrder

interface HistoryRepository {
    suspend fun getHistoryOrders(): List<HistoryOrder>
}