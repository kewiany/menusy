package xyz.kewiany.menusy.presentation.utils

data class ProductUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val ordered: Boolean,
    val quantity: Int = 0
) : UiItem