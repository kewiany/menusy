package xyz.kewiany.menusy.data.source.remote.api

import xyz.kewiany.menusy.data.source.remote.response.MenuResponse
import xyz.kewiany.menusy.data.source.remote.response.MenusResponse

interface MenuApi {
    suspend fun getMenus(placeId: String): MenusResponse?
    suspend fun getMenu(menuId: String): MenuResponse?
}