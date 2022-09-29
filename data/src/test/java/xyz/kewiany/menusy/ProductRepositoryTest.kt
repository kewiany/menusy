package xyz.kewiany.menusy

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import xyz.kewiany.menusy.data.ProductRepositoryImpl
import xyz.kewiany.menusy.data.network.api.ProductApi
import xyz.kewiany.menusy.data.network.response.ProductResponse
import xyz.kewiany.menusy.data.network.response.ProductsResponse
import xyz.kewiany.menusy.domain.repository.ProductRepository
import xyz.kewiany.menusy.model.Product
import xyz.kewiany.menusy.test.common.BaseTest
import xyz.kewiany.menusy.test.common.createProduct

class ProductRepositoryTest : BaseTest() {

    private val productId = "productId1"
    private val menuId = "menuId1"
    private val categoryId = "categoryId1"
    private val query = "query"
    private val product = createProduct(
        id = "productId1",
        menuId = "menuId"
    )
    private val products = listOf(createProduct(), createProduct())
    private val productsWithCategory = listOf(
        createProduct(categoryId = categoryId),
        createProduct(categoryId = categoryId)
    )
    private val productApi = mockk<ProductApi> {
        coEvery { getProduct(productId) } returns ProductResponse(product)
        coEvery { getProducts(menuId) } returns ProductsResponse(products)
        coEvery { getProducts(menuId, categoryId) } returns ProductsResponse(productsWithCategory)
        coEvery { getProductsByQuery(query) } returns ProductsResponse(products)
    }
    private val productRepository: ProductRepository = ProductRepositoryImpl(productApi)

    @Test
    fun getProduct_onResponse() = testScope.runTest {
        val result = productRepository.getProduct(productId)

        assertNotNull(result)
        assertEquals(productId, result.id)
        coVerify { productApi.getProduct(productId) }
    }

    @Test
    fun getProducts_onResponse() = testScope.runTest {
        val result = productRepository.getProducts(menuId)

        assertNotNull(result)
        assertEquals(products.size, result.size)
        coVerify { productApi.getProducts(menuId) }
    }

    @Test
    fun getProducts_onResponseNull() = testScope.runTest {
        coEvery { productApi.getProducts(menuId) } returns null
        val result = productRepository.getProducts(menuId)

        assertNotNull(result)
        assertEquals(emptyList<Product>(), result)
        coVerify { productApi.getProducts(menuId) }
    }

    @Test
    fun getProductsWithCategory_onResponse() = testScope.runTest {
        val result = productRepository.getProducts(menuId, categoryId)

        assertNotNull(result)
        assertEquals(products.size, result.size)
        coVerify { productApi.getProducts(menuId, categoryId) }
    }

    @Test
    fun getProductsWithCategory_onResponseNull() = testScope.runTest {
        coEvery { productApi.getProducts(productId, categoryId) } returns null
        val result = productRepository.getProducts(productId, categoryId)

        assertNotNull(result)
        assertEquals(emptyList<Product>(), result)
        coVerify { productApi.getProducts(productId, categoryId) }
    }

    @Test
    fun getProductsByQuery_onResponse() = testScope.runTest {
        val result = productRepository.getProductsByQuery(query)

        assertNotNull(result)
        assertEquals(products.size, result.size)
        coVerify { productApi.getProductsByQuery(query) }
    }

    @Test
    fun getProductsByQuery_onResponseNull() = testScope.runTest {
        coEvery { productApi.getProductsByQuery(query) } returns null
        val result = productRepository.getProductsByQuery(query)

        assertNotNull(result)
        assertEquals(emptyList<Product>(), result)
        coVerify { productApi.getProductsByQuery(query) }
    }
}