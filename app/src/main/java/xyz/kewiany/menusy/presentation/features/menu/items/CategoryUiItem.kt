package xyz.kewiany.menusy.presentation.features.menu.items

import xyz.kewiany.menusy.presentation.utils.UiItem

data class CategoryUiItem(
    override val id: String,
    val name: String
) : UiItem