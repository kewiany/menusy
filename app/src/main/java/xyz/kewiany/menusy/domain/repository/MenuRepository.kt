package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.Product

interface MenuRepository {
    suspend fun getMenus(): List<Menu>
    suspend fun getMenu(menuId: String): Menu
    suspend fun getProducts(menuId: String): List<Product>
    suspend fun getProducts(menuId: String, categoryId: String): List<Product>
    suspend fun getProductsByQuery(query: String): List<Product>
}