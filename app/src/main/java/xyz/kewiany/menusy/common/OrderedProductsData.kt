package xyz.kewiany.menusy.common

import xyz.kewiany.menusy.domain.model.OrderedProduct

data class OrderedProductsData(
    val products: List<OrderedProduct>,
    val totalQuantity: Int,
    val totalPrice: Float,
)