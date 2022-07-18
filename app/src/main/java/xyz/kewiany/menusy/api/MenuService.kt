package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.domain.model.Category
import xyz.kewiany.menusy.domain.model.Menu
import javax.inject.Inject

class MenuService @Inject constructor() : MenuApi {

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

    override suspend fun getMenus(): MenusResponse? {
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
}