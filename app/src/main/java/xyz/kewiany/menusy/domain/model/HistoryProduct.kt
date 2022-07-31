package xyz.kewiany.menusy.domain.model

import xyz.kewiany.menusy.presentation.utils.HistoryProductUiItem

data class HistoryProduct(
    val productId: Long = 0,
    val name: String,
    val description: String,
    val price: Float,
    val quantity: Int
)

fun HistoryProduct.asUIItem() = HistoryProductUiItem(
    id = productId.toString(),
    name = name,
    description = description,
    price = price.toString(),
    quantity = quantity.toString()
)