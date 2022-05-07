package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product

interface ProductApi {
    suspend fun getProducts(categoryId: String): ProductsResponse?
}

data class ProductsResponse(
    val products: List<Product>
)