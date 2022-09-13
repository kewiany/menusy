package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class FinishOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {

    suspend operator fun invoke() {
        val data = orderRepository.getOrderedProducts()
        orderRepository.saveOrderToHistory(
            products = data.products,
            date = "date",
            totalQuantity = data.totalQuantity,
            totalPrice = data.totalPrice
        )
    }
}