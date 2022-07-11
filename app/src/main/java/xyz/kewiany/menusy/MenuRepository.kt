package xyz.kewiany.menusy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import xyz.kewiany.menusy.api.MenuApi
import xyz.kewiany.menusy.api.ProductApi
import xyz.kewiany.menusy.entity.Menu
import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

interface MenuRepository {
    val needReloadProducts: Flow<Unit>
    suspend fun getMenu(menuId: String): Menu
    suspend fun getProducts(menuId: String): List<Product>
    suspend fun getProducts(menuId: String, categoryId: String): List<Product>
    suspend fun getProductsByQuery(query: String): List<Product>
    fun reloadMenuProducts()
}

class MenuRepositoryImpl @Inject constructor(
    private val menuApi: MenuApi,
    private val productsApi: ProductApi,
) : MenuRepository {

    private val _needReloadProducts = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    override val needReloadProducts: Flow<Unit> = _needReloadProducts

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

    override fun reloadMenuProducts() {
        _needReloadProducts.tryEmit(Unit)
    }
}