package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.common.CalendarNowProvider
import xyz.kewiany.menusy.common.nowInPoland
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class PurchaseUseCase @Inject constructor(
    private val nowProvider: CalendarNowProvider,
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke() {
        val now = nowProvider.nowInPoland
        orderRepository.saveOrderToHistory(
            date = now
        )
    }
}