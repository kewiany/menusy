package xyz.kewiany.menusy.presentation.utils

import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.domain.model.Product

data class ProductUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val ordered: Boolean,
    val quantity: Int = 0
) : UiItem

fun Product.asUIItem(quantity: Int = 0) = ProductUiItem(
    id = id,
    name = name,
    description = description,
    price = price.toString(),
    ordered = quantity != 0,
    quantity = quantity
)