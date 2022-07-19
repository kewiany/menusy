package xyz.kewiany.menusy.data.source.remote.api

import xyz.kewiany.menusy.data.source.remote.response.MenuResponse
import xyz.kewiany.menusy.data.source.remote.response.MenusResponse

interface MenuApi {
    suspend fun getMenu(menuId: String): MenuResponse?
    suspend fun getMenus(): MenusResponse?
}