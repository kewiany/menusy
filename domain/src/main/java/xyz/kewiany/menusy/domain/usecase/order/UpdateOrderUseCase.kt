package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.repository.OrderRepository
import xyz.kewiany.menusy.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(quantity: Int, productId: String) {
        val product = productRepository.getProduct(productId)
        orderRepository.updateOrder(quantity, product)
    }
}