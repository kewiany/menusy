package xyz.kewiany.menusy.data.source.remote.api

import xyz.kewiany.menusy.domain.model.Product

interface ProductApi {
    suspend fun getProducts(menuId: String): ProductsResponse?
    suspend fun getProducts(menuId: String, categoryId: String): ProductsResponse?
    suspend fun getProductsByQuery(query: String): ProductsResponse?
}

data class ProductsResponse(
    val products: List<Product>
)