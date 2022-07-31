package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.domain.model.HistoryOrder

interface HistoryRepository {
    suspend fun getHistoryOrders(): List<HistoryOrder>
}