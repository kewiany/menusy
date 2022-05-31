package xyz.kewiany.menusy.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.api.MenuApi
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.entity.Menu
import xyz.kewiany.menusy.entity.Product
import xyz.kewiany.menusy.usecase.GetMenuResponse.Error
import xyz.kewiany.menusy.usecase.GetMenuResponse.Success
import javax.inject.Inject

interface GetMenuUseCase {
    suspend operator fun invoke(menuId: String): GetMenuResponse
}

class GetMenuUseCaseImpl @Inject constructor(
    private val menuApi: MenuApi,
    private val productsApi: ProductApi
) : GetMenuUseCase {

    override suspend fun invoke(menuId: String): GetMenuResponse = withContext(Dispatchers.IO) {
        try {
            val menu = menuApi.getMenu(menuId)?.menu
            val products = mutableListOf<Product>()
            val categories = menu?.categories
            categories?.forEach { category ->
                products.addAll(productsApi.getProducts(menuId, category.id)?.products ?: emptyList())
            }
            val response = if (categories?.isNotEmpty() == true) {
                products
            } else {
                productsApi.getProducts(menuId)?.products ?: emptyList()
            }
            Success(requireNotNull(menu), response)
        } catch (e: Exception) {
            Error(GetMenuError.Unknown)
        }
    }
}

sealed class GetMenuResponse {
    data class Success(val menu: Menu, val products: List<Product>) : GetMenuResponse()
    data class Error(val error: GetMenuError) : GetMenuResponse()
}

sealed class GetMenuError {
    object Unknown : GetMenuError()
}