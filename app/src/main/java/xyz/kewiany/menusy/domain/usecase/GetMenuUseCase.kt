package xyz.kewiany.menusy.domain.usecase

import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.usecase.GetMenuResponse.Error
import xyz.kewiany.menusy.domain.usecase.GetMenuResponse.Success
import xyz.kewiany.menusy.presentation.utils.UiItem
import javax.inject.Inject

interface GetMenuUseCase {
    suspend operator fun invoke(menuId: String): GetMenuResponse
}

class GetMenuUseCaseImpl @Inject constructor(
    private val menuRepository: MenuRepository,
    private val dispatcherProvider: DispatcherProvider
) : GetMenuUseCase {

    override suspend fun invoke(menuId: String): GetMenuResponse = withContext(dispatcherProvider.io()) {
        try {
            val products = mutableListOf<Product>()

            val menu = menuRepository.getMenu(menuId)
            val categories = menu.categories

            val areCategories = categories?.isNotEmpty() == true

            if (areCategories) {
                categories?.forEach { category ->
                    products.addAll(menuRepository.getProducts(menuId, category.id))
                }
            } else {
                products.addAll(menuRepository.getProducts(menuId))
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

data class CachedMenu(val menu: Menu, val products: List<UiItem>)