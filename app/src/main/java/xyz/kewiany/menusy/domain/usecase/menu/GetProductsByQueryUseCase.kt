package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.OrderedProduct
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.repository.OrderRepository
import javax.inject.Inject

class GetProductsByQueryUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(query: String): GetProductsByQueryResponse = withContext(dispatcherProvider.io()) {
        try {
            val products = menuRepository.getProductsByQuery(query)
            val orderedProducts = orderRepository.getOrderedProducts()
            delay(1500)
            GetProductsByQueryResponse.Success(products, orderedProducts)
        } catch (e: Exception) {
            GetProductsByQueryResponse.Error(GetProductsError.Unknown)
        }
    }
}

sealed class GetProductsByQueryResponse {
    data class Success(
        val products: List<Product>,
        val orderedProducts: List<OrderedProduct>
    ) : GetProductsByQueryResponse()

    data class Error(val error: GetProductsError) : GetProductsByQueryResponse()
}

sealed class GetProductsByQueryError {
    object Unknown : GetProductsByQueryError()
}