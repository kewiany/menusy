package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.domain.model.Category

object CategoryMapper {
    fun map(category: Category) = CategoryUiItem(
        id = category.id,
        name = category.name
    )
}