package xyz.kewiany.menusy.presentation.utils

import xyz.kewiany.menusy.domain.model.Category

object CategoryMapper {
    fun map(category: Category) = CategoryUiItem(
        id = category.id,
        name = category.name
    )
}