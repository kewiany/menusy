package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product

interface ProductApi {
    suspend fun getProducts(menuId: String): ProductsResponse?
    suspend fun getProducts(menuId: String, categoryId: String): ProductsResponse?
}

data class ProductsResponse(
    val products: List<Product>
)