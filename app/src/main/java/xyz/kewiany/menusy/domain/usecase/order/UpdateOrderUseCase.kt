package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(quantity: Int, productId: String) {
        val product = menuRepository.getProduct(productId)
        orderRepository.updateOrder(quantity, product)
    }
}