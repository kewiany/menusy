package xyz.kewiany.menusy.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.api.ProductsResponse
import xyz.kewiany.menusy.createProduct

class GetProductsUseCaseTest : BaseTest() {

    private val menuId = "menuId"
    private val products = listOf(
        createProduct(),
        createProduct()
    )
    private val api = mockk<ProductApi>()
    private val useCase: GetProductsUseCase by lazy { GetProductsUseCaseImpl(api, testDispatcherProvider) }

    @Test
    fun given_responseNull_then_returnError() = testScope.runTest {
        coEvery { api.getProducts(any(), any()) } returns null
        val response = useCase(menuId)
        assertTrue(response is GetProductsResponse.Error)
    }

    @Test
    fun given_response_then_returnSuccess() = testScope.runTest {
        coEvery { api.getProducts(any(), any()) } returns ProductsResponse(products)
        val response = useCase(menuId)
        assertTrue(response is GetProductsResponse.Success)
        assertTrue((response as GetProductsResponse.Success).products == products)
    }
}