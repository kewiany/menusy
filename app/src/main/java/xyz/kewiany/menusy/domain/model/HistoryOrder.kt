package xyz.kewiany.menusy.domain.model

import xyz.kewiany.menusy.presentation.utils.HistoryOrderUiItem

data class HistoryOrder(
    val orderId: Long = 0,
    val date: String,
    val totalPrice: Float,
    val totalQuantity: Int,
    val products: List<HistoryProduct>
)

fun HistoryOrder.asUIItem() = HistoryOrderUiItem(
    id = orderId.toString(),
    date = date,
    totalPrice = totalPrice.toString(),
    totalQuantity = totalQuantity.toString()
)