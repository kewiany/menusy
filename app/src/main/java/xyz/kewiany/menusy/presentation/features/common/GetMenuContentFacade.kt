package xyz.kewiany.menusy.presentation.features.common

import kotlinx.coroutines.CancellationException
import org.slf4j.LoggerFactory
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.domain.model.Category
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuProductsUseCase
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.presentation.features.menu.items.CategoryTab
import xyz.kewiany.menusy.presentation.utils.obtainMenuContentUIItems
import javax.inject.Inject

class GetMenuContentFacade @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val getMenuProductsUseCase: GetMenuProductsUseCase,
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase
) {

    private val logger = LoggerFactory.getLogger(GetMenuContentFacade::class.java)

    suspend fun getContent(menuId: String): Result<Content> {
        return try {
            val menu = getMenu(menuId)
            val products = getMenuProducts(menuId)
            val orderedProducts = getOrderedProductsUseCase().products

            val tabs = menu.categories.toCategoryTabs()
            val items = obtainMenuContentUIItems(menu, products, orderedProducts)
            val content = Content(tabs, items)
            Result.Success(content)
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
        return this?.map { category -> CategoryTab(category.id, category.name) } ?: emptyList()
    }
}

data class Content(val tabs: List<CategoryTab>, val items: List<UiItem>)