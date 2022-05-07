package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

class ProductService @Inject constructor() : ProductApi {

    override suspend fun getProducts(menuId: String): ProductsResponse? {
        return try {
            val data = listOf(
                Product(
                    id = "0",
                    name = "chicken wings",
                    description = "crispy chicken wings, louisiana hot sauce, buttermilk blue cheese ranch dressing, carrot, celery",
                    price = "31.90",
                    menuId = "0"
                ),
                Product(
                    id = "1",
                    name = "salmon tatar",
                    description = "salmon, soy sauce, chives, cucumber, mustard, purple potato chips",
                    price = "36.90",
                    menuId = "0"
                ),
                Product(
                    id = "2",
                    name = "apple cake",
                    description = "warm apple cake with shortcrust pastry, vanilla ice cream",
                    price = "22.90",
                    menuId = "1"
                ),
                Product(
                    id = "3",
                    name = "tea",
                    description = "black, earl grey, green, jasmine, chamomile, mint",
                    price = "14.00",
                    menuId = "2"
                ),
            )
            ProductsResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}