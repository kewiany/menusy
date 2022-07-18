package xyz.kewiany.menusy.domain.usecase

import xyz.kewiany.menusy.data.source.local.entity.OrderWithProducts
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrdersFromHistoryUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(): List<OrderWithProducts> {
        return orderRepository.getOrdersFromHistory()
    }
}