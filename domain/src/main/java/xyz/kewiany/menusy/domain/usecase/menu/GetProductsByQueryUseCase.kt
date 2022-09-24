package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import xyz.kewiany.menusy.common.DispatcherProvider
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.repository.ProductRepository
import xyz.kewiany.menusy.model.Product
import javax.inject.Inject

class GetProductsByQueryUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    private val logger = LoggerFactory.getLogger(GetProductsByQueryUseCase::class.java)

    suspend operator fun invoke(query: String): Result<List<Product>> = withContext(dispatcherProvider.io()) {
        try {
            val products = productRepository.getProductsByQuery(query)
            Result.Success(products)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            logger.warn(e.stackTraceToString(), e)
            Result.Error(e)
        }
    }
}