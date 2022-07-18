package xyz.kewiany.menusy.data.repository

import xyz.kewiany.menusy.api.MenuApi
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val menuApi: MenuApi,
    private val productsApi: ProductApi,
) : MenuRepository {

    override suspend fun getMenu(menuId: String): Menu {
        val response = menuApi.getMenu(menuId)
        val menu = response?.menu; requireNotNull(menu)
        return menu
    }

    override suspend fun getProducts(menuId: String): List<Product> {
        val response = productsApi.getProducts(menuId)
        return response?.products ?: emptyList()
    }

    override suspend fun getProducts(menuId: String, categoryId: String): List<Product> {
        val response = productsApi.getProducts(menuId, categoryId)
        return response?.products ?: emptyList()
    }

    override suspend fun getProductsByQuery(query: String): List<Product> {
        val response = productsApi.getProductsByQuery(query)
        return response?.products ?: emptyList()
    }
}