package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem

open class ProductModifier {

    fun findProductById(items: List<UiItem>, productId: String): Pair<Int, ProductUiItem> {
        val index = items.indexOfFirst { it.id == productId }
        return index to items[index] as ProductUiItem
    }
}