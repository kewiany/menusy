package xyz.kewiany.menusy.domain.model

import xyz.kewiany.menusy.presentation.utils.CategoryUiItem

data class Category(
    val id: String,
    val name: String
)

fun Category.asUIItem() = CategoryUiItem(
    id = id,
    name = name
)