package xyz.kewiany.menusy.domain.model

import xyz.kewiany.menusy.presentation.utils.ProductUiItem
import java.math.BigDecimal

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val menuId: String,
    val categoryId: String? = null
)

fun Product.asUIItem(quantity: Int = 0) = ProductUiItem(
    id = id,
    name = name,
    description = description,
    price = price.toString(),
    ordered = quantity != 0,
    quantity = quantity
)