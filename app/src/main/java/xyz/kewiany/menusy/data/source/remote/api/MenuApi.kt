package xyz.kewiany.menusy.data.source.remote.api

import xyz.kewiany.menusy.domain.model.Menu

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