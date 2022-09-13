package xyz.kewiany.menusy.domain.model

import xyz.kewiany.menusy.presentation.utils.HistoryProductUiItem
import java.math.BigDecimal

data class HistoryProduct(
    val productId: Long = 0,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Int
)

fun HistoryProduct.asUIItem() = HistoryProductUiItem(
    id = productId.toString(),
    name = name,
    description = description,
    price = price.toString(),
    quantity = quantity.toString()
)