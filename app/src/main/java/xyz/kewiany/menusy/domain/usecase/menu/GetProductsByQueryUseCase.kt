package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class GetProductsByQueryUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(query: String): GetProductsByQueryResult = withContext(dispatcherProvider.io()) {
        try {
            val products = menuRepository.getProductsByQuery(query)
            delay(1500)
            GetProductsByQueryResult.Success(products)
        } catch (e: Exception) {
            GetProductsByQueryResult.Error(GetProductsError.Unknown)
        }
    }
}

sealed class GetProductsByQueryResult {

    data class Success(
        val products: List<Product>,
    ) : GetProductsByQueryResult()

    data class Error(val error: GetProductsError) : GetProductsByQueryResult()
}

sealed class GetProductsByQueryError {
    object Unknown : GetProductsByQueryError()
}