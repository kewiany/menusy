package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.model.HistoryOrder
import xyz.kewiany.menusy.domain.repository.HistoryRepository
import javax.inject.Inject

class GetOrdersFromHistoryUseCase @Inject constructor(private val historyRepository: HistoryRepository) {

    suspend operator fun invoke(): List<HistoryOrder> {
        return historyRepository.getHistoryOrders()
    }
}