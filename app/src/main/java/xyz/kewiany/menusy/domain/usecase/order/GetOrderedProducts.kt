package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrderedProducts @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(): List<OrderedProduct> {
        return orderRepository.getOrderedProducts()
    }
}