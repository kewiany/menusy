package xyz.kewiany.menusy

import org.junit.Assert.*
import org.junit.Test
import xyz.kewiany.menusy.android.common.ProductQuantityModifier
import xyz.kewiany.menusy.android.common.ProductUiItem
import xyz.kewiany.menusy.test.common.createProduct

class ProductQuantityModifierTest {

    private val productQuantityModifier = ProductQuantityModifier()

    @Test
    fun increaseQuantityOnZeroProductQuantity() {
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
        val (newQuantity, newItems) = requireNotNull(
            productQuantityModifier.increaseQuantity(products, product.id)
        )

        assertEquals(1, newQuantity)
        assertTrue((newItems.first { it.id == product.id } as ProductUiItem).quantity == 1)
    }

    @Test
    fun increaseQuantityOnOneProductQuantity() {
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
        val (newQuantity, newItems) = requireNotNull(
            productQuantityModifier.increaseQuantity(products, product.id)
        )

        assertEquals(2, newQuantity)
        assertTrue((newItems.first { it.id == product.id } as ProductUiItem).quantity == 2)
    }

    @Test
    fun decreaseQuantityOnOneProductQuantity() {
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
        val pair = productQuantityModifier.decreaseQuantity(products, product.id)
        assertNull(pair)
    }

    @Test
    fun decreaseQuantityOnTwoProductQuantity() {
        val product = createProductUiItem(
            quantity = 2,
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
        val (newQuantity, newItems) = requireNotNull(
            productQuantityModifier.decreaseQuantity(products, product.id)
        )

        assertEquals(1, newQuantity)
        assertTrue((newItems.first { it.id == product.id } as ProductUiItem).quantity == 1)
    }
}