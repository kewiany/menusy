package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product

interface ProductApi {
    suspend fun getProducts(menuId: String): ProductsResponse?
}

data class ProductsResponse(
    val products: List<Product>
)