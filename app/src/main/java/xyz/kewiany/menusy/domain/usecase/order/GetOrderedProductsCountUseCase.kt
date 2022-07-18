package xyz.kewiany.menusy.domain.usecase.order

import kotlinx.coroutines.flow.Flow
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class GetOrderedProductsCountUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    operator fun invoke(): Flow<Int> {
        return orderRepository.orderedProductsCount
    }
}