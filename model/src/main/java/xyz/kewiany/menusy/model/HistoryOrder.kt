package xyz.kewiany.menusy.model

import java.math.BigDecimal

data class HistoryOrder(
    val orderId: Long = 0,
    val date: String,
    val totalQuantity: Int,
    val totalPrice: BigDecimal,
    val products: List<HistoryProduct>,
    val placeName: String,
    val placeAddress: String
)