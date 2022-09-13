package xyz.kewiany.menusy.common

import xyz.kewiany.menusy.domain.model.OrderedProduct
import java.math.BigDecimal

data class OrderedProductsData(
    val products: List<OrderedProduct>,
    val totalQuantity: Int,
    val totalPrice: BigDecimal,
)