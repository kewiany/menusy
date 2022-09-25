package xyz.kewiany.menusy.feature.history

import xyz.kewiany.menusy.common.DateTimeFormatter
import xyz.kewiany.menusy.common.UiItem
import xyz.kewiany.menusy.model.HistoryOrder
import xyz.kewiany.menusy.model.HistoryProduct

interface HistoryUiItem : UiItem

data class HistoryOrderUiItem(
    override val id: String,
    val date: String,
    val totalPrice: String,
    val totalQuantity: String,
    val placeName: String,
    val placeAddress: String
) : HistoryUiItem

data class HistoryProductUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val quantity: String
) : HistoryUiItem

fun HistoryProduct.asUIItem() = HistoryProductUiItem(
    id = productId.toString(),
    name = name,
    description = description,
    price = price.toString(),
    quantity = quantity.toString()
)

fun HistoryOrder.asUIItem(dateTimeFormatter: DateTimeFormatter) = HistoryOrderUiItem(
    id = orderId.toString(),
    date = dateTimeFormatter.formatShortDateTimeWithDayOfWeek(date),
    totalPrice = totalPrice.toString(),
    totalQuantity = totalQuantity.toString(),
    placeName = placeName,
    placeAddress = placeAddress
)