package xyz.kewiany.menusy.domain.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val menuId: String,
    val categoryId: String? = null
)