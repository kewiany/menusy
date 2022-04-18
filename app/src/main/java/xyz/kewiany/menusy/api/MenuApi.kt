package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Menu

interface MenuApi {
    suspend fun getMenus(): MenusResponse?
}

data class MenusResponse(
    val menus: List<Menu>
)