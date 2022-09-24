package xyz.kewiany.menusy.data.network

import xyz.kewiany.menusy.data.network.api.MenuApi
import xyz.kewiany.menusy.data.network.response.MenuResponse
import xyz.kewiany.menusy.data.network.response.MenusResponse
import xyz.kewiany.menusy.model.Category
import xyz.kewiany.menusy.model.Menu
import javax.inject.Inject

class MenuService @Inject constructor() : MenuApi {

    override suspend fun getMenus(placeId: String): MenusResponse? {
        return try {
            val data = listOf(
                Menu("m0", "menu of the day"),
                Menu("m1", "main")
            )
            MenusResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMenu(menuId: String): MenuResponse? {
        return try {
            val data = if (menuId == "m0") {
                Menu(
                    "m0",
                    "menu of the day",
                    emptyList()
                )
            } else {
                Menu(
                    "m1",
                    "main",
                    listOf(
                        Category("c0", "entries"),
                        Category("c1", "desserts"),
                        Category("c2", "coffee and tea"),
                        Category("c3", "soft drinks")
                    )
                )
            }
            MenuResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}