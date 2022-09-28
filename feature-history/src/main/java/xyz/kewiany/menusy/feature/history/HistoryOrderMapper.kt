package xyz.kewiany.menusy.feature.history

import xyz.kewiany.menusy.common.DateTimeFormatter
import xyz.kewiany.menusy.model.HistoryOrder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryOrderMapper @Inject constructor(
    private val dateTimeFormatter: DateTimeFormatter
) {

    fun asUIItem(historyOrder: HistoryOrder): HistoryOrderUiItem = with(historyOrder) {
        HistoryOrderUiItem(
            id = orderId.toString(),
            date = dateTimeFormatter.formatShortDateTimeWithDayOfWeek(date),
            totalPrice = totalPrice.toString(),
            totalQuantity = totalQuantity.toString(),
            placeName = placeName,
            placeAddress = placeAddress
        )
    }
}