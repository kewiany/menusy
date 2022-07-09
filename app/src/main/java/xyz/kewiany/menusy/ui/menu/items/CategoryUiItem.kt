package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.utils.UiItem

data class CategoryUiItem(
    override val id: String,
    val name: String
) : UiItem

fun findCategoryIndex(items: List<UiItem>, index: String): Int = items.indexOfFirst { item ->
    if (item is CategoryUiItem) {
        item.id == index
    } else {
        false
    }
}