package xyz.kewiany.menusy.domain.model

import xyz.kewiany.menusy.presentation.utils.HistoryOrderUiItem
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

fun HistoryOrder.asUIItem() = HistoryOrderUiItem(
    id = orderId.toString(),
    date = date,
    totalPrice = totalPrice.toString(),
    totalQuantity = totalQuantity.toString(),
    placeName = placeName,
    placeAddress = placeAddress
)