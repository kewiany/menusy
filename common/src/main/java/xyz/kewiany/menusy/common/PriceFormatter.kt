package xyz.kewiany.menusy.common

import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class PriceFormatter @Inject constructor() {

    private val logger = LoggerFactory.getLogger(PriceFormatter::class.java)

    fun format(amount: BigDecimal, currencyCode: String): String {
        val currency = Currency.valueOf(currencyCode)
        val amountString = format(amount).replace(".", currency.separator)
        return "$amountString${Typography.nbsp}${currency.symbol}"
    }

    private fun format(amount: BigDecimal): String {
        val amountTruncated = if (amount.isInteger()) amount.setScale(0) else amount.setScale(2, RoundingMode.HALF_UP)
        return amountTruncated.toPlainString()
    }

    private fun BigDecimal.isInteger(): Boolean {
        return try {
            intValueExact()
            true
        } catch (e: ArithmeticException) {
            false
        }
    }

    enum class Currency(val symbol: String, val separator: String) {
        PLN("zł", "."),
    }
}
