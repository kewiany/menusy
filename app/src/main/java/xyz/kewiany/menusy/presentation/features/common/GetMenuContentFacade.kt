package xyz.kewiany.menusy.presentation.features.common

import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.model.Category
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuProductsUseCase
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuUseCase
import xyz.kewiany.menusy.domain.usecase.order.GetOrderedProductsUseCase
import xyz.kewiany.menusy.presentation.features.menu.items.CategoryTab
import xyz.kewiany.menusy.presentation.utils.UiItem
import xyz.kewiany.menusy.presentation.utils.obtainMenuContentUIItems
import javax.inject.Inject

class GetMenuContentFacade @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val getMenuProductsUseCase: GetMenuProductsUseCase,
    private val getOrderedProductsUseCase: GetOrderedProductsUseCase
) {
    suspend fun getContent(menuId: String): Result<Content> {
        val menu = getMenu(menuId) ?: return Result.Error()
        val products = getMenuProducts(menuId) ?: return Result.Error()
        val orderedProducts = getOrderedProductsUseCase()

        val tabs = menu.categories.toCategoryTabs()
        val items = obtainMenuContentUIItems(menu, products, orderedProducts)
        val content = Content(tabs, items)

        return Result.Success(content)
    }

    private suspend fun getMenu(menuId: String) = when (val result = getMenuUseCase(menuId)) {
        is Result.Success -> {
            result.data
        }
        is Result.Error -> {
            null
        }
    }

    private suspend fun getMenuProducts(menuId: String) = when (val result = getMenuProductsUseCase(menuId)) {
        is Result.Success -> {
            result.data
        }
        is Result.Error -> {
            null
        }
    }

    private fun List<Category>?.toCategoryTabs(): List<CategoryTab> {
        return this?.map { category -> CategoryTab(category.id, category.name) } ?: emptyList()
    }
}

data class Content(val tabs: List<CategoryTab>, val items: List<UiItem>)