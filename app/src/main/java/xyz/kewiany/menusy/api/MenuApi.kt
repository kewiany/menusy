package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Menu

interface MenuApi {
    suspend fun getMenu(menuId: String): MenuResponse?
    suspend fun getMenus(): MenusResponse?
}

data class MenuResponse(
    val menu: Menu
)

data class MenusResponse(
    val menus: List<Menu>
)