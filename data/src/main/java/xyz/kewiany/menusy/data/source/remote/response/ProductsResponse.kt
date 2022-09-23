package xyz.kewiany.menusy.data.source.remote.response

import xyz.kewiany.menusy.domain.model.Product

data class ProductsResponse(
    val products: List<Product>
)