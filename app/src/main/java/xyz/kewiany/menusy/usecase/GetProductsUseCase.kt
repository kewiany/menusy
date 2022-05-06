package xyz.kewiany.menusy.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.entity.Product

interface GetProductsUseCase {
    suspend operator fun invoke(menuId: String): GetProductsResponse
}

class GetProductsUseCaseImpl(private val productApi: ProductApi) : GetProductsUseCase {

    override suspend fun invoke(menuId: String): GetProductsResponse = withContext(Dispatchers.IO) {
        try {
            val response = productApi.getProducts(menuId)
            val data = response?.products
            delay(1500)
            GetProductsResponse.Success(requireNotNull(data))
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