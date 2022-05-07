package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.utils.UiItem

data class ProductUiItem(
    val id: String,
    val name: String,
    val description: String,
    val price: String
) : UiItem