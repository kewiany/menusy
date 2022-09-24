package xyz.kewiany.menusy.domain.usecase.order

import xyz.kewiany.menusy.domain.repository.OrderRepository
import xyz.kewiany.menusy.model.OrderedProductsData
import javax.inject.Inject

class GetOrderedProductsUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(): OrderedProductsData {
        return orderRepository.getOrderedProductsData()
    }
}