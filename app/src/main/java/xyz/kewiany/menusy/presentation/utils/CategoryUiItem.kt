package xyz.kewiany.menusy.presentation.utils

import xyz.kewiany.menusy.domain.model.Category

data class CategoryUiItem(
    override val id: String,
    val name: String
) : UiItem

fun Category.asUIItem() = CategoryUiItem(
    id = id,
    name = name
)