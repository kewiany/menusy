package xyz.kewiany.menusy.android.common

import xyz.kewiany.menusy.common.PriceFormatter
import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.model.Product

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
    priceFormatter: PriceFormatter
) = ProductUiItem(
    id = id,
    name = name,
    description = description,
    price = priceFormatter.format(price, "PLN"),
    ordered = quantity != 0,
    quantity = quantity
)