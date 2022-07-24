package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsByQueryUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(query: String): Result<List<Product>> = withContext(dispatcherProvider.io()) {
        try {
            val products = productRepository.getProductsByQuery(query)
            delay(1500)
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}