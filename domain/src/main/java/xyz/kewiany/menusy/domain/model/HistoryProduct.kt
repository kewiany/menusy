package xyz.kewiany.menusy.domain.model

import java.math.BigDecimal

data class HistoryProduct(
    val productId: Long = 0,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Int,
)