package xyz.kewiany.menusy.data.repository

import xyz.kewiany.menusy.data.source.remote.api.MenuApi
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuApi: MenuApi,
) : MenuRepository {

    override suspend fun getMenus(): List<Menu> {
        val response = menuApi.getMenus()
        return response?.menus ?: emptyList()
    }

    override suspend fun getMenu(menuId: String): Menu {
        val response = menuApi.getMenu(menuId)
        val menu = response?.menu; requireNotNull(menu)
        return menu
    }
}