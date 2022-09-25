package xyz.kewiany.menusy.feature.menu

import kotlinx.coroutines.CancellationException
import org.slf4j.LoggerFactory
import xyz.kewiany.menusy.android.common.ContentBuilder
import xyz.kewiany.menusy.common.CategoryTab
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuProductsUseCase
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.model.Category
import xyz.kewiany.menusy.model.Menu
import xyz.kewiany.menusy.model.Product
import javax.inject.Inject

class GetMenuContentFacade @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val getMenuProductsUseCase: GetMenuProductsUseCase,
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase,
    private val contentBuilder: ContentBuilder
) {

    private val logger = LoggerFactory.getLogger(GetMenuContentFacade::class.java)

    suspend fun getContent(menuId: String): Result<MenuContentData> {
        return try {
            val menu = getMenu(menuId)
            val categories = menu.categories ?: emptyList()
            val products = getMenuProducts(menuId)
            val orderedProducts = getOrderedProductsUseCase().products

            val tabs = categories.toCategoryTabs()
            val items = contentBuilder.buildContent(
                categories = categories,
                products = products,
                orderedProducts = orderedProducts
            )
            val menuContentData = MenuContentData(tabs, items)
            Result.Success(menuContentData)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            logger.warn(e.stackTraceToString(), e)
            Result.Error(e)
        }
    }

    private suspend fun getMenu(menuId: String): Menu {
        val result = getMenuUseCase(menuId) as Result.Success
        return result.data
    }

    private suspend fun getMenuProducts(menuId: String): List<Product> {
        val result = getMenuProductsUseCase(menuId) as Result.Success
        return result.data
    }

    private fun List<Category>?.toCategoryTabs(): List<CategoryTab> {
        return this?.map { category -> CategoryTab(category.id, category.name) }
            ?: emptyList()
    }
}