package xyz.kewiany.menusy.data.network.api

import xyz.kewiany.menusy.data.network.response.MenuResponse
import xyz.kewiany.menusy.data.network.response.MenusResponse

interface MenuApi {
    suspend fun getMenus(placeId: String): MenusResponse?
    suspend fun getMenu(menuId: String): MenuResponse?
}