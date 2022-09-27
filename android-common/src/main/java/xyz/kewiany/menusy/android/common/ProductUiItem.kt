package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.model.Product
import java.math.BigDecimal

data class ProductUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val ordered: Boolean,
    val quantity: Int = 0
) : UiItem

fun Product.asUIItem(
    quantity: Int = 0,
    formatPrice: (BigDecimal) -> String,
) = ProductUiItem(
    id = id,
    name = name,
    description = description,
    price = formatPrice(price),
    ordered = quantity != 0,
    quantity = quantity
)