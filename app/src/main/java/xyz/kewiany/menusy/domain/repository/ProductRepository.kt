package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.domain.model.Product

interface ProductRepository {
    suspend fun getProduct(id: String): Product
    suspend fun getProducts(menuId: String): List<Product>
    suspend fun getProducts(menuId: String, categoryId: String): List<Product>
    suspend fun getProductsByQuery(query: String): List<Product>
}