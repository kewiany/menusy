package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.model.Category

data class CategoryUiItem(
    override val id: String,
    val name: String
) : UiItem

fun Category.asUIItem() = CategoryUiItem(
    id = id,
    name = name
)