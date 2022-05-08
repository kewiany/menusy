package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

class ProductService @Inject constructor() : ProductApi {

    override suspend fun getProducts(menuId: String): ProductsResponse? {
        return get(menuId, null)
    }

    override suspend fun getProducts(menuId: String, categoryId: String): ProductsResponse? {
        return get(menuId, categoryId)
    }

    private fun get(menuId: String?, categoryId: String?): ProductsResponse {
        return try {
            val data = if (menuId == "0") {
                listOf(
                    Product(
                        id = "0",
                        name = "tomato soup",
                        description = "italian tomatoes, basiland a touch of fennel",
                        price = "10.90",
                        menuId = "0"
                    )
                )
            } else {
                when (categoryId) {
                    "0" -> {
                        listOf(
                            Product(
                                id = "0",
                                name = "chicken wings",
                                description = "crispy chicken wings, louisiana hot sauce, buttermilk blue cheese ranch dressing, carrot, celery",
                                price = "31.90",
                                menuId = "1",
                                categoryId = "0"
                            ),
                            Product(
                                id = "1",
                                name = "salmon tatar",
                                description = "salmon, soy sauce, chives, cucumber, mustard, purple potato chips",
                                price = "36.90",
                                menuId = "1",
                                categoryId = "0"
                            ),
                        )
                    }
                    "1" -> {
                        listOf(
                            Product(
                                id = "2",
                                name = "apple cake",
                                description = "warm apple cake with shortcrust pastry, vanilla ice cream",
                                price = "22.90",
                                menuId = "1",
                                categoryId = "1"
                            )
                        )
                    }
                    "2" -> {
                        listOf(
                            Product(
                                id = "3",
                                name = "tea",
                                description = "black, earl grey, green, jasmine, chamomile, mint",
                                price = "14.00",
                                menuId = "1",
                                categoryId = "2"
                            ),
                            Product(
                                id = "4",
                                name = "almond frappe",
                                description = "black or with milk",
                                price = "18.00",
                                menuId = "1",
                                categoryId = "2"
                            )
                        )
                    }
                    "3" -> {
                        listOf(
                            Product(
                                id = "5",
                                name = "toma juice",
                                description = "apple, orange, grapefruit, tomato",
                                price = "9.00",
                                menuId = "1",
                                categoryId = "3"
                            ),
                            Product(
                                id = "6",
                                name = "cisowianka",
                                description = "still, sparkling",
                                price = "19.00",
                                menuId = "1",
                                categoryId = "3"
                            )
                        )
                    }
                    else -> emptyList()
                }
            }
            ProductsResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}