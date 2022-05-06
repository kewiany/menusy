package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product

class ProductService : ProductApi {

    override suspend fun getProducts(menuId: String): ProductsResponse? {
        return try {
            val data = listOf(Product("0", "zero"), Product("1", "one"))
            ProductsResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}