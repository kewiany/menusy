package xyz.kewiany.menusy.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import xyz.kewiany.menusy.common.DateTimeFormatter
import xyz.kewiany.menusy.feature.history.HistoryOrderMapper
import xyz.kewiany.menusy.test.common.createHistoryOrder
import xyz.kewiany.menusy.test.common.createHistoryProduct
import java.time.Clock
import java.time.LocalDateTime
import java.util.*

class HistoryOrderMapperTest {

    private val date = LocalDateTime.now()
    private val clock = Clock.systemUTC()
    private val locale = Locale.ENGLISH
    private val dateTimeFormatter = DateTimeFormatter(clock, locale)

    private val historyOrderMapper = HistoryOrderMapper(dateTimeFormatter)

    @Test
    fun map() {
        val products = listOf(
            createHistoryProduct()
        )
        val historyOrder = createHistoryOrder(
            date = date,
            products = products,
        )
        val result = historyOrderMapper.asUIItem(historyOrder)

        assertEquals(historyOrder.orderId.toString(), result.id)
        assertTrue(result.date.contains(date.minute.toString()) && result.date.contains(date.dayOfMonth.toString()))
        assertEquals(historyOrder.totalPrice.toString(), result.totalPrice)
        assertEquals(historyOrder.totalQuantity.toString(), result.totalQuantity)
        assertEquals(historyOrder.placeName, result.placeName)
        assertEquals(historyOrder.placeAddress, result.placeAddress)
    }
}