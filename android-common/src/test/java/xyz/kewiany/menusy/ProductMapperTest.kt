package xyz.kewiany.menusy

import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.android.common.ProductMapper
import xyz.kewiany.menusy.common.PriceFormatter
import xyz.kewiany.menusy.test.common.createProduct
import java.math.BigDecimal

class ProductMapperTest {

    private val priceFormatter = PriceFormatter()
    private val productMapper = ProductMapper(priceFormatter)

    @Test
    fun map() {
        val product = createProduct()
        val result = productMapper.asUIItem(product = product)

        assertEquals(product.id, result.id)
        assertEquals(product.name, result.name)
        assertEquals(product.description, result.description)
    }

    @Test
    fun mapWithQuantityZero() {
        val quantity = 0
        val product = createProduct()

        val result = productMapper.asUIItem(quantity, product)

        assertEquals(quantity, result.quantity)
        assertFalse(result.ordered)
    }

    @Test
    fun mapWithQuantityOne() {
        val quantity = 1
        val product = createProduct()

        val result = productMapper.asUIItem(quantity, product)

        assertEquals(quantity, result.quantity)
        assertTrue(result.ordered)
    }

    @Test
    fun mapWithPrice() {
        val quantity = 1
        val price = BigDecimal.valueOf(12.25)
        val product = createProduct(price = price)

        val result = productMapper.asUIItem(quantity, product)

        assertTrue(result.price.contains(price.toString()))
    }
}