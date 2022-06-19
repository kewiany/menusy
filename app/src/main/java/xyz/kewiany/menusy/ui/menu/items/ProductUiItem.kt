package xyz.kewiany.menusy.ui.menu.items

import xyz.kewiany.menusy.utils.UiItem

data class ProductUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val quantity: Int = 0
) : UiItem