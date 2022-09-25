package xyz.kewiany.menusy

import org.junit.Assert.assertEquals
import org.junit.Test
import xyz.kewiany.menusy.common.PriceFormatter
import kotlin.text.Typography.nbsp

class PriceFormatterTest {

    private val formatter = PriceFormatter()

    @Test
    fun pln0point0() {
        val actual = formatter.format(0.0.toBigDecimal(), "PLN")
        assertEquals("0${nbsp}zł", actual)
    }

    @Test
    fun pln25point0() {
        val actual = formatter.format(25.0.toBigDecimal(), "PLN")
        assertEquals("25${nbsp}zł", actual)
    }

    @Test
    fun pln25point00() {
        val actual = formatter.format(25.00.toBigDecimal(), "PLN")
        assertEquals("25${nbsp}zł", actual)
    }

    @Test
    fun pln25point000() {
        val actual = formatter.format(25.000.toBigDecimal(), "PLN")
        assertEquals("25${nbsp}zł", actual)
    }

    @Test
    fun pln2500point000() {
        val actual = formatter.format(2500.000.toBigDecimal(), "PLN")
        assertEquals("2500${nbsp}zł", actual)
    }

    @Test
    fun pln25point005() {
        val actual = formatter.format(25.005.toBigDecimal(), "PLN")
        assertEquals("25.01${nbsp}zł", actual)
    }

    @Test
    fun pln25point5() {
        val actual = formatter.format(25.5.toBigDecimal(), "PLN")
        assertEquals("25.50${nbsp}zł", actual)
    }

    @Test
    fun pln25point50() {
        val actual = formatter.format(25.50.toBigDecimal(), "PLN")
        assertEquals("25.50${nbsp}zł", actual)
    }
}
