package xyz.kewiany.menusy

import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.android.common.ProductSelectionModifier
import xyz.kewiany.menusy.android.common.ProductUiItem
import xyz.kewiany.menusy.test.common.createProduct

class ProductSelectionModifierTest {

    private val productSelectionModifier = ProductSelectionModifier()

    @Test
    fun selectUnselectedProduct() {
        val product = createProductUiItem(
            quantity = 0,
            product = createProduct(
                id = "id2"
            )
        )
        val products = listOf(
            createProductUiItem(
                quantity = 0,
                product = createProduct(
                    id = "id1",
                ),
            ),
            product,
            createProductUiItem(
                quantity = 0,
                product = createProduct(
                    id = "id3"
                )
            ),
        )
        val (newQuantity, newItems) = productSelectionModifier.select(products, product.id)

        assertEquals(1, newQuantity)
        assertTrue((newItems.first { it.id == product.id } as ProductUiItem).ordered)
    }

    @Test
    fun unselectSelectedProduct() {
        val product = createProductUiItem(
            quantity = 1,
            product = createProduct(
                id = "id2"
            )
        )
        val products = listOf(
            createProductUiItem(
                quantity = 0,
                product = createProduct(
                    id = "id1",
                ),
            ),
            product,
            createProductUiItem(
                quantity = 0,
                product = createProduct(
                    id = "id3"
                )
            ),
        )
        val (newQuantity, newItems) = productSelectionModifier.select(products, product.id)

        assertEquals(0, newQuantity)
        assertFalse((newItems.first { it.id == product.id } as ProductUiItem).ordered)
    }
}