package xyz.kewiany.menusy.data.source.remote.api

import xyz.kewiany.menusy.data.source.remote.response.ProductResponse
import xyz.kewiany.menusy.data.source.remote.response.ProductsResponse

interface ProductApi {
    suspend fun getProduct(productId: String): ProductResponse?
    suspend fun getProducts(menuId: String): ProductsResponse?
    suspend fun getProducts(menuId: String, categoryId: String): ProductsResponse?
    suspend fun getProductsByQuery(query: String): ProductsResponse?
}

