package xyz.kewiany.menusy.domain.model

import java.math.BigDecimal

data class OrderedProductsData(
    val products: List<OrderedProduct>,
    val totalQuantity: Int,
    val totalPrice: BigDecimal,
)