package xyz.kewiany.menusy.data

import xyz.kewiany.menusy.data.network.api.MenuApi
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.model.Menu
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuApi: MenuApi,
) : MenuRepository {

    override suspend fun getMenus(placeId: String): List<Menu> {
        val response = menuApi.getMenus(placeId)
        return response?.menus ?: emptyList()
    }

    override suspend fun getMenu(menuId: String): Menu {
        val response = menuApi.getMenu(menuId)
        val menu = response?.menu; checkNotNull(menu)
        return menu
    }
}