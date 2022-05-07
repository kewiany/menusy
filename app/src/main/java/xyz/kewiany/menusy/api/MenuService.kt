package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Menu
import javax.inject.Inject

class MenuService @Inject constructor() : MenuApi {

    override suspend fun getMenus(): MenusResponse? {
        return try {
            val data = listOf(Menu("0", "zero"), Menu("1", "one"))
            MenusResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}