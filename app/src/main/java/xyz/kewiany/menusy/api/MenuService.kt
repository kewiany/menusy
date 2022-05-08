package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Category
import xyz.kewiany.menusy.entity.Menu
import javax.inject.Inject

class MenuService @Inject constructor() : MenuApi {

    override suspend fun getMenu(menuId: String): MenuResponse? {
        return try {
            val data = if (menuId == "0") {
                Menu(
                    "0",
                    "menu of the day",
                    emptyList()
                )
            } else {
                Menu(
                    "1",
                    "main",
                    listOf(
                        Category("0", "entries"),
                        Category("1", "desserts"),
                        Category("2", "coffee and tea"),
                        Category("3", "soft drinks")
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
                Menu("0", "menu of the day"),
                Menu("1", "main")
            )
            MenusResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}