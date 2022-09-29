package xyz.kewiany.menusy

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import xyz.kewiany.menusy.data.MenuRepositoryImpl
import xyz.kewiany.menusy.data.network.api.MenuApi
import xyz.kewiany.menusy.data.network.response.MenuResponse
import xyz.kewiany.menusy.data.network.response.MenusResponse
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.model.Menu
import xyz.kewiany.menusy.test.common.BaseTest
import xyz.kewiany.menusy.test.common.createMenu

class MenuRepositoryTest : BaseTest() {

    private val menuId = "id1"
    private val menu = createMenu(
        id = menuId
    )
    private val menus = listOf(menu, createMenu(), createMenu())
    private val menuApi = mockk<MenuApi> {
        coEvery { getMenu(any()) } returns MenuResponse(menu)
        coEvery { getMenus(any()) } returns MenusResponse(menus)
    }
    private val menuRepository: MenuRepository = MenuRepositoryImpl(menuApi)

    @Test
    fun getMenus_onResponse() = testScope.runTest {
        val placeId = "placeId"
        val menus = menuRepository.getMenus(placeId)

        assertNotNull(menus)
        coVerify { menuApi.getMenus(placeId) }
    }

    @Test
    fun getMenus_onResponseNull() = testScope.runTest {
        coEvery { menuApi.getMenus(any()) } returns null
        val placeId = "placeId"
        val menus = menuRepository.getMenus(placeId)

        assertEquals(emptyList<Menu>(), menus)
        coVerify { menuApi.getMenus(placeId) }
    }


    @Test
    fun getMenu_onResponse() = testScope.runTest {
        val menu = menuRepository.getMenu(menuId)

        assertNotNull(menu)
        coVerify { menuApi.getMenu(menuId) }
    }

    @Test
    fun getMenu_onResponseWithNull() = testScope.runTest {
        //TODO
    }
}