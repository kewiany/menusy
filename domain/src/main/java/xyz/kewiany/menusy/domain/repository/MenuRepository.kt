package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.model.Menu

interface MenuRepository {
    suspend fun getMenus(placeId: String): List<Menu>
    suspend fun getMenu(menuId: String): Menu
}