package xyz.kewiany.menusy

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import xyz.kewiany.menusy.android.common.CategoryUiItem
import xyz.kewiany.menusy.android.common.ContentBuilder
import xyz.kewiany.menusy.android.common.ProductUiItem
import xyz.kewiany.menusy.common.PriceFormatter
import xyz.kewiany.menusy.model.Category
import xyz.kewiany.menusy.model.OrderedProduct
import xyz.kewiany.menusy.model.Product
import xyz.kewiany.menusy.test.common.createCategory
import xyz.kewiany.menusy.test.common.createOrderedProduct
import xyz.kewiany.menusy.test.common.createProduct

class ContentBuilderTest {

    private val priceFormatter = mockk<PriceFormatter> {
        every { format(any(), any()) } returns "price"
    }
    private val contentBuilder = ContentBuilder(priceFormatter)

    @Test
    fun buildEmptyList() {
        val categories = emptyList<Category>()
        val products = emptyList<Product>()
        val orderedProducts = emptyList<OrderedProduct>()

        val content = contentBuilder.buildContent(
            categories = categories,
            products = products,
            orderedProducts = orderedProducts
        )

        content.iterator().run {
            assertFalse(hasNext())
        }
    }

    @Test
    fun buildList() {
        val categories = emptyList<Category>()
        val products = listOf(
            createProduct(id = "id1"),
            createProduct(id = "id2")
        )
        val orderedProducts = emptyList<OrderedProduct>()

        val content = contentBuilder.buildContent(
            categories = categories,
            products = products,
            orderedProducts = orderedProducts
        )

        content.iterator().run {
            (next()).also {
                assertTrue(it is ProductUiItem)
                assertFalse((it as ProductUiItem).ordered)
            }
            (next()).also {
                assertTrue(it is ProductUiItem)
                assertFalse((it as ProductUiItem).ordered)
            }
            assertFalse(hasNext())
        }
    }

    @Test
    fun buildListWithOrderedProducts() {
        val categories = emptyList<Category>()
        val product = createProduct(id = "id2")
        val orderedProduct = createOrderedProduct(quantity = 2, product = product)
        val products = listOf(
            createProduct(id = "id1"),
            product
        )
        val orderedProducts = listOf(
            orderedProduct
        )

        val content = contentBuilder.buildContent(
            categories = categories,
            products = products,
            orderedProducts = orderedProducts
        )

        content.iterator().run {
            (next()).also {
                assertTrue(it is ProductUiItem)
                it as ProductUiItem
                assertFalse(it.ordered)
            }
            (next()).also {
                assertTrue(it is ProductUiItem)
                it as ProductUiItem
                assertTrue(it.ordered)
                assertTrue(it.quantity == orderedProduct.quantity)
            }
            assertFalse(hasNext())
        }
    }

    @Test
    fun buildListWithCategories() {
        val categories = listOf(
            createCategory(id = "id1"),
            createCategory(id = "id2")
        )
        val products = listOf(
            createProduct(id = "id1", categoryId = categories[0].id),
            createProduct(id = "id2", categoryId = categories[0].id),
            createProduct(id = "id3", categoryId = categories[1].id),
        )
        val orderedProducts = emptyList<OrderedProduct>()

        val content = contentBuilder.buildContent(
            categories = categories,
            products = products,
            orderedProducts = orderedProducts
        )
        content.iterator().run {
            assertTrue(next() is CategoryUiItem)
            assertTrue(next() is ProductUiItem)
            assertTrue(next() is ProductUiItem)
            assertTrue(next() is CategoryUiItem)
            assertTrue(next() is ProductUiItem)
            assertFalse(hasNext())
        }
    }

    @Test
    fun buildListWithOrderedProducts_and_withCategories() {
        val categories = listOf(
            createCategory(id = "id1"),
            createCategory(id = "id2")
        )
        val products = listOf(
            createProduct(id = "id1", categoryId = categories[0].id),
            createProduct(id = "id2", categoryId = categories[0].id),
            createProduct(id = "id3", categoryId = categories[1].id),
        )
        val orderedProducts = listOf(
            createOrderedProduct(quantity = 1, product = products[0]),
            createOrderedProduct(quantity = 2, product = products[2])
        )

        val content = contentBuilder.buildContent(
            categories = categories,
            products = products,
            orderedProducts = orderedProducts
        )

        content.iterator().run {
            assertTrue(next() is CategoryUiItem)
            (next()).also {
                assertTrue(it is ProductUiItem)
                it as ProductUiItem
                assertTrue(it.ordered)
                assertTrue(it.quantity == orderedProducts[0].quantity)
            }
            assertTrue(next() is ProductUiItem)
            assertTrue(next() is CategoryUiItem)
            (next()).also {
                assertTrue(it is ProductUiItem)
                it as ProductUiItem
                assertTrue(it.ordered)
                assertTrue(it.quantity == orderedProducts[1].quantity)
            }
            assertFalse(hasNext())
        }
    }
}