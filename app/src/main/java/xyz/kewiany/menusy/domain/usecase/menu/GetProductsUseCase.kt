package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(menuId: String): GetProductsResponse = withContext(dispatcherProvider.io()) {
        try {
            val data = menuRepository.getProducts(menuId, "")
            delay(1500)
            GetProductsResponse.Success(data)
        } catch (e: Exception) {
            GetProductsResponse.Error(GetProductsError.Unknown)
        }
    }
}


sealed class GetProductsResponse {
    data class Success(val products: List<Product>) : GetProductsResponse()
    data class Error(val error: GetProductsError) : GetProductsResponse()
}

sealed class GetProductsError {
    object Unknown : GetProductsError()
}