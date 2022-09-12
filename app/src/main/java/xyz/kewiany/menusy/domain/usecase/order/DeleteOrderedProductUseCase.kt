package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class DeleteOrderedProductUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(productId: String) {
        orderRepository.deleteOrder(productId)
    }
}