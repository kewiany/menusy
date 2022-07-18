package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(quantity: Int, product: Product) {
        orderRepository.updateOrder(quantity, product)
    }
}