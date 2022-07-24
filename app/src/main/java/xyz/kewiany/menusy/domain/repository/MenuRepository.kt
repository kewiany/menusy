package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.domain.model.Menu

interface MenuRepository {
    suspend fun getMenus(): List<Menu>
    suspend fun getMenu(menuId: String): Menu
}