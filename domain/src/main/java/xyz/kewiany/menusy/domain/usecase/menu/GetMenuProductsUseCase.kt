package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import xyz.kewiany.menusy.common.DispatcherProvider
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.repository.ProductRepository
import xyz.kewiany.menusy.model.Menu
import xyz.kewiany.menusy.model.Product
import javax.inject.Inject

class GetMenuProductsUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    private val logger = LoggerFactory.getLogger(GetMenuProductsUseCase::class.java)

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
        if (e is CancellationException) throw e
        logger.warn(e.stackTraceToString(), e)
        Result.Error(e)
    }

    private suspend fun getMenu(menuId: String): Menu = withContext(dispatcherProvider.io()) {
        menuRepository.getMenu(menuId)
    }

    private suspend fun getProducts(
        menuId: String, categoryId: String? = null
    ): List<Product> = withContext(dispatcherProvider.io()) {
        if (categoryId == null) {
            productRepository.getProducts(menuId)
        } else {
            productRepository.getProducts(menuId, categoryId)
        }
    }
}