package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.entity.Category

object CategoryMapper {
    fun map(category: Category) = CategoryUiItem(
        id = category.id,
        name = category.name
    )
}