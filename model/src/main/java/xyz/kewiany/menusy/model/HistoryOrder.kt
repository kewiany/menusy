package xyz.kewiany.menusy.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class HistoryOrder(
    val orderId: Long = 0,
    val date: LocalDateTime,
    val totalQuantity: Int,
    val totalPrice: BigDecimal,
    val products: List<HistoryProduct>,
    val placeName: String,
    val placeAddress: String
)