package xyz.kewiany.menusy.ui.search

import xyz.kewiany.menusy.utils.UiItem

data class SearchUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val quantity: Int = 0
) : UiItem