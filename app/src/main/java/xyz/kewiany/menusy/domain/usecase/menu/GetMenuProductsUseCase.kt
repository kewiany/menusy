package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class GetMenuProductsUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(menuId: String): Result<List<Product>> = try {
        val products = mutableListOf<Product>()

        val menu = getMenu(menuId)
        val categories = menu.categories
        val categoriesInMenu = categories?.isNotEmpty() == true

        if (categoriesInMenu) {
            categories?.forEach { category -> getProducts(menuId, category.id).also(products::addAll) }
        } else {
            getProducts(menuId).also(products::addAll)
        }
        Result.Success(products)
    } catch (e: Exception) {
        Result.Error(e)
    }

    private suspend fun getMenu(menuId: String): Menu = withContext(dispatcherProvider.io()) {
        menuRepository.getMenu(menuId)
    }

    private suspend fun getProducts(
        menuId: String, categoryId: String? = null
    ): List<Product> = withContext(dispatcherProvider.io()) {
        if (categoryId == null) {
            menuRepository.getProducts(menuId)
        } else {
            menuRepository.getProducts(menuId, categoryId)
        }
    }
}