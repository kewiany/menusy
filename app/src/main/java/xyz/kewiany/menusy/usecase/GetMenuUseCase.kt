package xyz.kewiany.menusy.usecase

import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.api.MenuApi
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.entity.Menu
import xyz.kewiany.menusy.entity.Product
import xyz.kewiany.menusy.usecase.GetMenuResponse.Error
import xyz.kewiany.menusy.usecase.GetMenuResponse.Success
import xyz.kewiany.menusy.utils.DispatcherProvider
import javax.inject.Inject

interface GetMenuUseCase {
    suspend operator fun invoke(menuId: String): GetMenuResponse
}

class GetMenuUseCaseImpl @Inject constructor(
    private val menuApi: MenuApi,
    private val productsApi: ProductApi,
    private val dispatcherProvider: DispatcherProvider
) : GetMenuUseCase {

    override suspend fun invoke(menuId: String): GetMenuResponse = withContext(dispatcherProvider.io()) {
        try {
            val products = mutableListOf<Product>()

            val menu = menuApi.getMenu(menuId)?.menu; requireNotNull(menu)
            val categories = menu.categories

            val areCategories = categories?.isNotEmpty() == true

            if (areCategories) {
                categories?.forEach { category ->
                    products.addAll(productsApi.getProducts(menuId, category.id)?.products ?: emptyList())
                }
            } else {
                products.addAll(productsApi.getProducts(menuId)?.products ?: emptyList())
            }
            Success(menu, products)
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