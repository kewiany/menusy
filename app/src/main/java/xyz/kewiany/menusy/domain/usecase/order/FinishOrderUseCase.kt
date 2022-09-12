package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class FinishOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke() {
        val orderedProducts = orderRepository.getOrderedProducts()
        orderRepository.saveOrderToHistory(orderedProducts)
    }
}