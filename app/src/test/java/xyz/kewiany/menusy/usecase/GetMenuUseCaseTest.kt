package xyz.kewiany.menusy.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import xyz.kewiany.menusy.BaseTest
import xyz.kewiany.menusy.createCategory
import xyz.kewiany.menusy.createMenu
import xyz.kewiany.menusy.createProduct
import xyz.kewiany.menusy.data.source.remote.api.MenuApi
import xyz.kewiany.menusy.data.source.remote.api.MenuResponse
import xyz.kewiany.menusy.data.source.remote.api.ProductApi
import xyz.kewiany.menusy.data.source.remote.api.ProductsResponse
import xyz.kewiany.menusy.domain.usecase.GetMenuUseCaseImpl
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuResponse
import xyz.kewiany.menusy.domain.usecase.menu.GetMenuUseCase

class GetMenuUseCaseTest : BaseTest() {

    private val menuId = "menuId"
    private val categories = listOf(
        createCategory(),
        createCategory()
    )
    private val menu = createMenu(
        categories = categories
    )
    private val products = listOf(
        createProduct(), createProduct()
    )
    private val menuApi = mockk<MenuApi>()
    private val productApi = mockk<ProductApi> {
        coEvery { getProducts(any()) } returns ProductsResponse(emptyList())
        coEvery { getProducts(any(), any()) } returns ProductsResponse(emptyList())
    }
    private val useCase: GetMenuUseCase by lazy { GetMenuUseCaseImpl(menuApi, productApi, testDispatcherProvider) }

    @Test
    fun given_getMenuResponseNull_then_returnError() = testScope.runTest {
        coEvery { menuApi.getMenu(any()) } returns null
        val response = useCase(menuId)
        assertTrue(response is GetMenuResponse.Error)
    }

    @Test
    fun given_getMenuResponse_then_returnSuccess() = testScope.runTest {
        coEvery { menuApi.getMenu(any()) } returns MenuResponse(menu)
        val response = useCase(menuId)
        assertTrue(response is GetMenuResponse.Success)
        response as GetMenuResponse.Success
        assertEquals(menu, response.menu)
    }

    @Test
    fun given_getMenuResponse_and_withCategories_then_getProductsWithCategories() = testScope.runTest {
        coEvery { menuApi.getMenu(any()) } returns MenuResponse(menu)
        useCase(menuId)
        coVerify { productApi.getProducts(any(), any()) }
    }

    @Test
    fun given_getMenuResponse_withCategories_then_returnMenu_and_products() = testScope.runTest {
        coEvery { menuApi.getMenu(any()) } returns MenuResponse(menu)
        coEvery { productApi.getProducts(any(), any()) } returns ProductsResponse(products)
        val response = useCase(menuId)
        response as GetMenuResponse.Success
        val expected = products + products
        assertEquals(expected, response.products)
    }

    @Test
    fun given_getMenuResponse_and_withoutCategories_then_getProducts() = testScope.runTest {
        coEvery { menuApi.getMenu(any()) } returns MenuResponse(createMenu(categories = emptyList()))
        coEvery { productApi.getProducts(any()) } returns ProductsResponse(products)
        useCase(menuId)
        coVerify { productApi.getProducts(any()) }
    }

    @Test
    fun given_getMenuResponse_withoutCategories_then_returnMenu_and_products() = testScope.runTest {
        coEvery { menuApi.getMenu(any()) } returns MenuResponse(createMenu(categories = emptyList()))
        coEvery { productApi.getProducts(any()) } returns ProductsResponse(products)
        val response = useCase(menuId)
        response as GetMenuResponse.Success
        val expected = products
        assertEquals(expected, response.products)
    }
}