package xyz.kewiany.menusy.data.repository

import xyz.kewiany.menusy.data.source.remote.api.ProductApi
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productsApi: ProductApi,
) : ProductRepository {

    override suspend fun getProduct(id: String): Product {
        val response = productsApi.getProduct(id)
        val product = response?.product; requireNotNull(product)
        return product
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